package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Passager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class InscriptionPassagerController implements Serializable {
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
    private TextField emailField;

    @FXML
    private TextField villeField;

    @FXML
    private Button suivantButton;
    @FXML
    private void initialize(){
        Conducteur conducteur=null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Conducteur.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            conducteur = (Conducteur) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            System.out.println("Conducteur non inscris");
        }
        if (conducteur != null) {
            nomField.setText(conducteur.getNom());
            prenomField.setText(conducteur.getPrenom());
            telephoneField.setText(conducteur.getTelephone());
            villeField.setText(conducteur.getVille());
            emailField.setText(conducteur.getEmail());
        }
    }

    @FXML
    private void siSuivant(ActionEvent event) throws IOException {
        if (isInputValid()) {
            Passager passager;
            if (emailField.getText() != null && !emailField.getText().isEmpty()) {
                passager = new Passager(nomField.getText(), prenomField.getText(), telephoneField.getText(), villeField.getText(), emailField.getText());
            } else {
                passager = new Passager(nomField.getText(), prenomField.getText(), telephoneField.getText(), villeField.getText());
            }
            // Envoyer l'objet Passager via un socket
            try (Socket socket = new Socket("localhost", 12346);
                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                oos.writeObject(passager);
            }

            // Sauvegarder le passager pour une utilisation ultérieure
            FileOutputStream fileOutputStream = new FileOutputStream("Passager.txt");
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(passager);
            fileOutputStream.close();
            objectOutputStream.close();

            System.out.println("Formulaire valide. Continuer...");
            sceneManager.addScene("MenuPassagerApp", "MenuPassager-view.fxml");
            sceneManager.switchTo("MenuPassagerApp");
        }
    }

    @FXML
    private void siAnnuler(ActionEvent event) {
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
        if (emailField.getText() != null && !emailField.getText().isEmpty() && !emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage += "Email invalide!\n";
        }
        if (villeField.getText() == null || villeField.getText().length() == 0) {
            errorMessage += "Ville invalide!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Champs Invalides");
            alert.setHeaderText("Corrigez les champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    public static class InscriptionPassagerApp extends Application implements Serializable {

        public static void main(String[] args) {
            launch(args);
        }
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(InscriptionPassagerApp.class.getResource("InscriptionPassager-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Inscription Passager!");
            stage.setScene(scene);
            stage.show();

        }

    }
}
