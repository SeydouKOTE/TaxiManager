package Metier.Serveur;

import Metier.Coordonnees;
import com.dlsc.gmapsfx.javascript.object.LatLong;

import java.io.*;
import java.net.*;
import java.util.*;

public class TaxiServer implements Serializable{
    private static final int PORT = 12345;
    private static List<DriverHandler> drivers = Collections.synchronizedList(new ArrayList<>());
    private static List<PassengerHandler> passengers = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        new TaxiServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                ObjectOutputStream out1 = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in1 = new ObjectInputStream(socket.getInputStream());

                String clientType = in.readUTF(); // "DRIVER" or "PASSENGER"

                if ("DRIVER".equals(clientType)) {
                    DriverHandler driverHandler = new DriverHandler(socket, in, out,in1,out1);
                    drivers.add(driverHandler);
                    driverHandler.start();
                } else if ("PASSENGER".equals(clientType)) {
                    PassengerHandler passengerHandler = new PassengerHandler(socket, in, out,in1,out1);
                    passengers.add(passengerHandler);
                    passengerHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DriverHandler extends Thread {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private ObjectOutputStream out1;
        private ObjectInputStream in1;
        private boolean available = true;
        private PassengerHandler passengerHandler;

        public DriverHandler(Socket socket, DataInputStream in, DataOutputStream out,ObjectInputStream in1,ObjectOutputStream out1) {
            this.socket = socket;
            this.in = in;
            this.out = out;
            this.in1=in1;
            this.out1 = out1;
        }

        public void run() {
            try {
                out.writeUTF("WAITING_FOR_PASSENGER");
                synchronized (drivers) {
                    while (available) {
                        drivers.wait();
                    }
                }

                Message input;
                while ((input = (Message) in1.readObject()) != null) {
                    if (passengerHandler != null) {
                        passengerHandler.sendMessage(input);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setPassengerHandler(PassengerHandler passengerHandler) {
            this.passengerHandler = passengerHandler;
        }

        public void sendMessage(Message message) {
            try {
                out1.writeObject(message);
                out1.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void sendMessage00(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void closeConnections() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (in1 != null) in1.close();
                if (out1 != null) out1.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PassengerHandler extends Thread {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        private ObjectOutputStream out1;
        private ObjectInputStream in1;
        private DriverHandler driverHandler;
        private LatLong L1;
        private LatLong L2;
        private Coordonnees coordonnees;

        public PassengerHandler(Socket socket, DataInputStream in, DataOutputStream out,ObjectInputStream in1,ObjectOutputStream out1) {
            this.socket = socket;
            this.in = in;
            this.out = out;
            this.in1=in1;
            this.out1 = out1;
        }

        public void run() {
            try {
                System.out.println("Coordonnees reçu.");
                coordonnees = (Coordonnees) in1.readObject();

                findDriverForPassenger(this);

                Message input;
                while ((input = (Message) in1.readObject()) != null) {
                    if (driverHandler != null) {
                        driverHandler.sendMessage(input);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }

        public void setDriverHandler(DriverHandler driverHandler) {
            this.driverHandler = driverHandler;
        }

        public void sendMessage(Message message) {
            try {
                out1.writeObject(message);
                out1.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void sendMessage00(String message) {
            try {
                out.writeUTF(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void closeConnections() {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (in1 != null) in1.close();
                if (out1 != null) out1.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void findDriverForPassenger(PassengerHandler passenger) {
        synchronized (drivers) {
            for (DriverHandler driver : drivers) {
                if (driver.isAvailable()) {
                    driver.setPassengerHandler(passenger);
                    passenger.setDriverHandler(driver);
                    driver.setAvailable(false);
                    driver.sendMessage00("PASSENGER_FOUND");

                    // Transmit the file coordinates.txt to the driver
                    try {
                        driver.out1.writeObject(passenger.coordonnees);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    passenger.sendMessage00("DRIVER_FOUND");
                    synchronized (drivers) {
                        drivers.notifyAll();
                    }
                    return;
                }
            }
            passenger.sendMessage00("NO_DRIVER_AVAILABLE");
        }
    }
}
