package Metier.Serveur;
import Metier.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurObjet implements Serializable{

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12346)) {
            System.out.println("Serveur en attente de connexions...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

                    Object obj = ois.readObject();
                    if (obj instanceof Passager) {
                        Passager passager = (Passager) obj;
                        System.out.println("Reçu Passager: " + passager);
                        ImplDeclarationMethodes id = new ImplDeclarationMethodes();
                        id.addPassager(passager);

                    } else if (obj instanceof Conducteur) {
                        Conducteur conducteur = (Conducteur) obj;
                        System.out.println("Reçu Conducteur: " + conducteur);
                        ImplDeclarationMethodes id = new ImplDeclarationMethodes();
                        id.addConducteur(conducteur);

                    }else if (obj instanceof Payer) {
                        Payer payer = (Payer) obj;
                        System.out.println("Reçu Passager: " + payer);
                        ImplDeclarationMethodes id = new ImplDeclarationMethodes();
                        Long l1=payer.getConducteur().getIdentifiant_C();
                        Long l2=payer.getPaiement().getId_paiement();
                        id.addPayer(l1,l2,payer.getDateDebut(),payer.getDateFin());

                    }
                    else if (obj instanceof Reserver) {
                        Reserver reserver = (Reserver) obj;
                        System.out.println("Reçu Passager: " + reserver);
                        ImplDeclarationMethodes id = new ImplDeclarationMethodes();
                        id.addReserver(reserver);

                    }else {
                        System.out.println("Objet inconnu reçu.");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

