package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Coordonnees;
import Metier.Paiement;
import Metier.Payer;
import Metier.Serveur.Message;
import Metier.Serveur.Profile;
import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.MapComponentInitializedListener;
import com.dlsc.gmapsfx.javascript.object.*;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Base64;

public class MapConducteurController implements MapComponentInitializedListener, Serializable{
    public ImageView ouvrirOufermer;
    public AnchorPane paiement;
    private boolean isPageOpen = true;
    public TextField inputField;
    public WebView messageArea;
    public Button loadButton;
    private Socket socket;
    private ObjectOutputStream out1;
    private ObjectInputStream in1;
    private DataInputStream in ;
    private DataOutputStream out ;
    private Profile profile;
    private WebEngine webEngine;
    private Coordonnees coordonnees;

    public AnchorPane discution;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private CheckBox ActiverLaVisibilite;
    private GoogleMap map;
    private volatile boolean running = true;
    @FXML
    private Button monthlyButton;

    @FXML
    private Button quarterlyButton;

    @FXML
    private Button annualButton;

    @FXML
    private Label confirmationLabel;

    @FXML
    private VBox paymentDetails;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cardHolderNameField;

    @FXML
    private TextField expiryDateField;

    @FXML
    private TextField cvvField;

    private String selectedPlan;
    private int selectedAmount;
    private Payer payer;

    @FXML
    private void initialize() {
        mapView.addMapInitializedListener(this);
        discution.setVisible(false);
        loadButton.setVisible(false);
        ouvrirOufermer.setVisible(false);
        paiement.setVisible(false);
        monthlyButton.setOnAction(event -> handlePaymentSelection("Mensuel", 50));
        quarterlyButton.setOnAction(event -> handlePaymentSelection("Trimestriel", 140));
        annualButton.setOnAction(event -> handlePaymentSelection("Annuel", 500));
    }

    @Override
    public void mapInitialized() {
        MapOptions options = new MapOptions();
        options.center(new LatLong(33.6860700, -7.3829800)) // Example coordinates, set to your desired default location
                .zoom(14)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(true)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.SATELLITE);

        map = mapView.createMap(options);
    }

    @FXML
    private void handleLoadButtonAction() {
        if(coordonnees!=null){
            LatLong startLatLong = new LatLong(coordonnees.getLatitude1(),coordonnees.getAltitude1());
            LatLong endLatLong =  new LatLong(coordonnees.getLatitude2(),coordonnees.getAltitude2());
            Marker startMarker = new Marker(new MarkerOptions().position(startLatLong).title("Start Location"));
            Marker endMarker = new Marker(new MarkerOptions().position(endLatLong).title("End Location"));

            map.addMarker(startMarker);
            map.addMarker(endMarker);
            map.setCenter(startLatLong);
            map.setZoom(15);
        }
        else showAlert("Error", "Failed to load coordinates.");
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public void onActiverLaVisibilite(ActionEvent actionEvent) {
        Payer payer =null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Payer.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            payer = (Payer) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            System.out.println("erreur pour le paiement");
        }
        if (payer==null){
            showAlert("Error", "Vous devez payer les frais pour recevoire les passagers.");
            paiement.setVisible(true);
            ActiverLaVisibilite.setSelected(false);
        }else if( payer.getDateFin().isBefore(LocalDate.now())) {
            showAlert("Error", "Votre date de paiement a expiré. Veuillez renouveler votre paiement pour continuer à recevoire les passagers.");
            paiement.setVisible(true);
            ActiverLaVisibilite.setSelected(false);
        }
        if (ActiverLaVisibilite.isSelected()){
            try {
                showAlert("OK", "Attente des Passagers.");

                try {
                    profile = new Profile("Conducteur", "Online");
                    socket = new Socket("localhost", 12345);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());
                    out1 = new ObjectOutputStream(socket.getOutputStream());
                    in1 = new ObjectInputStream(socket.getInputStream());
                    webEngine = messageArea.getEngine();
                    injectCssStyles();  // Inject CSS styles
                    out.writeUTF("DRIVER");

                    String confirmation = in.readUTF();
                    if ("WAITING_FOR_PASSENGER".equals(confirmation)) {
                        System.out.println("En attente d'un passager...");

                        confirmation = in.readUTF();
                        if ("PASSENGER_FOUND".equals(confirmation)) {
                            System.out.println("Un passager a été trouvé.");

                            coordonnees= (Coordonnees) in1.readObject();
                            System.out.println("Coordonnées reçu");
                            showAlert("OK", "Un Passager a été trouver.");
                            Platform.runLater(()->discution.setVisible(true));
                            Platform.runLater(()->ouvrirOufermer.setVisible(true));
                            Platform.runLater(() -> loadButton.setVisible(true));
                            System.out.println("Fichier coordinates.txt reçu du passager.");
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
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error", "Failed to connect.");
            }
        } else {

        }
    }
    public void handleFinButtonClick(ActionEvent actionEvent) {
    }

    public void handleAnnulerButtonClick(ActionEvent actionEvent) {
    }
    @FXML
    public void onStopCommunication(ActionEvent actionEvent) {
        running = false;
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
    private void handlePaymentSelection(String plan, int amount) {
        selectedPlan = plan;
        selectedAmount = amount;
        confirmationLabel.setText("Vous avez choisi le plan " + plan + " - " + amount + " DHS.");
        paymentDetails.setVisible(true);
        paymentDetails.setManaged(true);
    }

    @FXML
    private void handlePaymentProcessing() {
        String cardNumber = cardNumberField.getText();
        String cardHolderName = cardHolderNameField.getText();
        String expiryDate = expiryDateField.getText();
        String cvv = cvvField.getText();
        Conducteur conducteur=null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Conducteur.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            conducteur = (Conducteur) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            System.out.println("Erreur dans le paiement pour charger le conducteur");
        }
        if (selectedAmount==77){
            payer = new Payer(conducteur,new Paiement("MENSUEL",50.));
        } else if (selectedAmount==225) {
            payer = new Payer(conducteur,new Paiement("TRIMESUEL",140.));
        }
        else {
            payer = new Payer(conducteur,new Paiement("ANNUELLE",500.));
        }

        // Valider et traiter les informations de paiement ici
        // Pour l'instant, nous allons simplement afficher un message de confirmation
        try (Socket socket = new Socket("localhost", 12346);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject(payer);
            FileOutputStream fileOutputStream = new FileOutputStream("Payer.txt");
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(payer);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (validateCardDetails(cardNumber, cardHolderName, expiryDate, cvv)) {
            confirmationLabel.setText("Paiement de " + selectedAmount + " DHS pour le plan " + selectedPlan + " effectué avec succès!");
            paiement.setVisible(false);
            ActiverLaVisibilite.setSelected(true);
        } else {
            confirmationLabel.setText("Erreur dans les informations de la carte bancaire. Veuillez vérifier et réessayer.");
            ActiverLaVisibilite.setSelected(false);
        }
    }

    private boolean validateCardDetails(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        // Ajouter des validations réelles ici
        return !cardNumber.isEmpty() && !cardHolderName.isEmpty() && !expiryDate.isEmpty() && !cvv.isEmpty();
    }

    public void handleAbandonnerButtonClick(ActionEvent actionEvent) {
        paiement.setVisible(false);
        ActiverLaVisibilite.setSelected(false);
    }

    public static class MapConducteurApp extends Application implements Serializable {

        @Override
        public void start(Stage primaryStage) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MapConducteur-view.fxml"));
                AnchorPane root = loader.load();
                Scene scene = new Scene(root, 700, 400);
                //primaryStage.setTitle("Saved Coordinates Map");
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
