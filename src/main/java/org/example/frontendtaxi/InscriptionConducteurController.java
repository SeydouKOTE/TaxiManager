package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Passager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class InscriptionConducteurController implements Serializable {
    @FXML
    private SceneManager sceneManager;
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField telephoneField;

    @FXML
    private DatePicker dateDeNaissancePicker;

    @FXML
    private TextField emailField;

    @FXML
    private TextField villeField;

    @FXML
    private TextField numeroPermisField;

    @FXML
    private ChoiceBox<String> sexeChoiceBox;

    @FXML
    private Button suivantButton;

    @FXML
    private void initialize() {
        // Initialisation du choix par défaut pour le sexe
        sexeChoiceBox.setValue("Homme");
        Passager passager=null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Passager.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            passager = (Passager) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            System.out.println("Passager non inscris");
        }
        if (passager != null) {
            nomField.setText(passager.getNom());
            prenomField.setText(passager.getPrenom());
            telephoneField.setText(passager.getTelephone());
            villeField.setText(passager.getVille());
        }
    }

    @FXML
    private void siSuivant(ActionEvent event) throws IOException {
        if (isInputValid()) {
            // Création de l'objet Conducteur
            Conducteur conducteur = new Conducteur(nomField.getText(), prenomField.getText(), dateDeNaissancePicker.getValue(),telephoneField.getText(),villeField.getText(), emailField.getText(), numeroPermisField.getText(), sexeChoiceBox.getValue());
            // Envoyer l'objet Conducteur via un socket
            try (Socket socket = new Socket("localhost", 12346);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                oos.writeObject(conducteur);
            }
            // Sauvegarder l'objet Conducteur pour une utilisation ultérieure
            FileOutputStream fileOutputStream = new FileOutputStream("Conducteur.txt");
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(conducteur);
            fileOutputStream.close();
            objectOutputStream.close();


            // Continuer à la scène suivante
            System.out.println("Formulaire valide. Continuer...");
            sceneManager.addScene("MenuConducteurApp", "MenuConducteur-view.fxml");
            sceneManager.switchTo("MenuConducteurApp");
        }
    }

    @FXML
    private void siAnnuler(ActionEvent event) {
        // Logique pour le bouton annuler
        System.out.println("Formulaire annulé.");
        System.exit(0);
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().length() == 0) {
            errorMessage += "Nom invalide!\n";
        }
        if (prenomField.getText() == null || prenomField.getText().length() == 0) {
            errorMessage += "Prénom invalide!\n";
        }
        if (telephoneField.getText() == null || !telephoneField.getText().matches("\\d+")) {
            errorMessage += "Téléphone invalide (doit contenir uniquement des chiffres)!\n";
        }
        if (dateDeNaissancePicker.getValue() == null) {
            errorMessage += "Date de naissance invalide!\n";
        }
        if (emailField.getText() == null || !emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage += "Email invalide!\n";
        }
        if (villeField.getText() == null || villeField.getText().length() == 0) {
            errorMessage += "Ville invalide!\n";
        }
        if (numeroPermisField.getText() == null || numeroPermisField.getText().length() == 0) {
            errorMessage += "Numéro de permis invalide!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Affiche un message d'alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs Invalides");
            alert.setHeaderText("Corrigez les champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public static class InscriptionConducteurApp extends Application implements Serializable {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(InscriptionConducteurApp.class.getResource("InscriptionConducteur-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Inscription Conducteur!");
            stage.setScene(scene);
            stage.show();
        }
    }
}
