package org.example.frontendtaxi;

import Metier.Coordonnees;
import Metier.Serveur.Message;
import Metier.Serveur.Profile;
import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.event.GMapMouseEvent;
import com.dlsc.gmapsfx.javascript.event.UIEventType;
import com.dlsc.gmapsfx.javascript.object.*;
import com.dlsc.gmapsfx.service.directions.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;

public class MapPassagerController implements MapComponentInitializedListener, Serializable, DirectionsServiceCallback {
    public ImageView ouvrirOufermer;
    private boolean isPageOpen = true;
    public TextField inputField;
    public WebView messageArea;
    private Socket socket;
    private ObjectOutputStream out1;
    private ObjectInputStream in1;
    private DataInputStream in;
    private DataOutputStream out;
    private Profile profile;
    private WebEngine webEngine;
    private Coordonnees coordonnees;
    private DirectionsService directionsService;
    private DirectionsRenderer directionsRenderer;

    public AnchorPane discution;
    public Button chercherUnConducteurButton;
    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;
    private LatLong startLatLong;
    private LatLong endLatLong;
    private Marker startMarker;
    private Marker endMarker;
    protected DirectionsPane directions;

    @FXML
    private void initialize() {
        mapView.addMapInitializedListener(this);
        discution.setVisible(false);
        ouvrirOufermer.setVisible(false);
    }
    private void requestDirections(LatLong from, LatLong to) {
        DirectionsRequest request = new DirectionsRequest(from, to, TravelModes.DRIVING,false);
        directionsService.getRoute(request, this,directionsRenderer);
    }
    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();
        options.center(new LatLong(33.6860700, -7.3829800))
                .zoom(14)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.SATELLITE);
        map = mapView.createMap(options);
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
            LatLong latLong = event.getLatLong();
            if (startLatLong == null) {
                startLatLong = latLong;
                startMarker = new Marker(new MarkerOptions().position(latLong).title("Start Location"));
                map.addMarker(startMarker);
            } else if (endLatLong == null) {
                endLatLong = latLong;
                endMarker = new Marker(new MarkerOptions().position(latLong).title("End Location"));
                map.addMarker(endMarker);
            } else {
                clearMarkers();
                startLatLong = latLong;
                startMarker = new Marker(new MarkerOptions().position(latLong).title("Start Location"));
                map.addMarker(startMarker);
            }
        });
        directions = mapView.getDirec();
        directionsRenderer=new DirectionsRenderer(true, map, directions);
        directionsService = new DirectionsService();
    }

    @FXML
    public void onSearchDriverClick(ActionEvent event) {
        if (startLatLong != null && endLatLong != null) {
            coordonnees = new Coordonnees(startLatLong.getLatitude(), startLatLong.getLongitude(), endLatLong.getLatitude(), endLatLong.getLongitude());
            requestDirections(new LatLong(startLatLong.getLatitude(), startLatLong.getLongitude()),new LatLong(endLatLong.getLatitude(), endLatLong.getLongitude()));
            chercherUnConducteurButton.setVisible(false);
            try {
                profile = new Profile("Passager", "Online");
                socket = new Socket("localhost", 12345);
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                out1 = new ObjectOutputStream(socket.getOutputStream());
                in1 = new ObjectInputStream(socket.getInputStream());
                out.writeUTF("PASSENGER");

                System.out.println("La coordonnée a été envoyé.");
                out1.writeObject(coordonnees);
                webEngine = messageArea.getEngine();
                injectCssStyles();  // Inject CSS styles

                String confirmation = in.readUTF();
                if ("DRIVER_FOUND".equals(confirmation)) {
                    System.out.println("Un chauffeur a été trouvé.");
                    showAlert("OK", "Un Conducteur a été trouver.");
                    Platform.runLater(() -> discution.setVisible(true));
                    Platform.runLater(()->ouvrirOufermer.setVisible(true));
                    new Thread(() -> {
                        try {
                            Object message;
                            while ((message = in1.readObject()) != null) {
                                if (message instanceof Message) {
                                    Message msg = (Message) message;
                                    Platform.runLater(() -> displayMessage(msg));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    System.out.println("Aucun chauffeur disponible.");
                    showAlert("Erreur", "Aucun chauffeur disponible.");
                    chercherUnConducteurButton.setText("Chercher à nouveau");
                    chercherUnConducteurButton.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de l'envoi des coordonnées.");
            }

        } else {
            showAlert("Avertissement.", "S’il vous plaît, sélectionnez à la fois le point de départ et le point d’arrêt.");
        }
    }

    private void clearMarkers() {
        if (startMarker != null) {
            map.removeMarker(startMarker);
            startMarker = null;
        }
        if (endMarker != null) {
            map.removeMarker(endMarker);
            endMarker = null;
        }
        startLatLong = null;
        endLatLong = null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
    @FXML
    private void handleouvrirOufermerClick() {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), discution);
        if (isPageOpen) {
            transition.setByX(discution.getWidth());
            Image image = new Image(getClass().getResource("/org/example/frontendtaxi/Images/flecheGauche.png").toExternalForm());
            ouvrirOufermer.setImage(image);
        } else {
            transition.setByX(-discution.getWidth());
            Image image = new Image(getClass().getResource("/org/example/frontendtaxi/Images/flecheDroite.png").toExternalForm());
            ouvrirOufermer.setImage(image);
        }
        transition.play();
        isPageOpen = !isPageOpen;
    }
    @FXML
    public void sendMessage() {
        try {
            String messageText = inputField.getText();
            Message message = new Message("TEXT", messageText, profile);
            out1.writeObject(message);
            out1.flush();
            Platform.runLater(() -> displayMessage(message));
            inputField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sendImage() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                FileInputStream fis = new FileInputStream(selectedFile);
                byte[] imageBytes = fis.readAllBytes();
                fis.close();

                Message message = new Message("IMAGE", imageBytes, profile);
                out1.writeObject(message);
                out1.flush();
                Platform.runLater(() -> displayMessage(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void injectCssStyles() {
        String css = ".sent { text-align: right; color: blue; margin-left: auto; max-width: 60%; background-color: #e1ffc7; padding: 10px; border-radius: 10px; } " +
                ".received { text-align: left; color: green; margin-right: auto; max-width: 60%; background-color: #f1f0f0; padding: 10px; border-radius: 10px; } " +
                "img { display: block; margin: 5px 0; }";
        String script = "var style = document.createElement('style'); style.innerHTML = '" + css + "'; document.head.appendChild(style);";
        webEngine.executeScript(script);
    }

    private void displayMessage(Message msg) {
        boolean isCurrentUser = msg.getEnvoyerProfile().getNom().equals(profile.getNom());

        if ("IMAGE".equals(msg.getType())) {
            byte[] imageBytes = (byte[]) msg.getContenu();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            String imageHtml = "<img src=\"data:image/png;base64," + base64Image + "\" style=\"width: 100px; height: 100px; object-fit: cover; max-width: 100%;\"/>";
            String script = String.format(
                    "document.body.innerHTML += '<div class=\"%s\"><b>%s</b> envoie une image:<br>%s<br></div>';",
                    isCurrentUser ? "sent" : "received",
                    escapeJavaScript(msg.getEnvoyerProfile().getNom()),
                    imageHtml
            );
            webEngine.executeScript(script);
        } else {
            String script = String.format(
                    "document.body.innerHTML += '<div class=\"%s\"><b>%s:</b> %s<br></div>';",
                    isCurrentUser ? "sent" : "received",
                    escapeJavaScript(msg.getEnvoyerProfile().getNom()),
                    escapeJavaScript(msg.getContenu().toString())
            );
            webEngine.executeScript(script);
        }
    }

    private String escapeJavaScript(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("'", "\\'").replace("\"", "\\\"");
    }

    @Override
    public void directionsReceived(DirectionsResult directionsResult, DirectionStatus directionStatus) {

    }

    public static class MapPassagerApp extends Application implements Serializable {
        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MapPassager-view.fxml")));

            primaryStage.setScene(new Scene(root, 700, 400));
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
