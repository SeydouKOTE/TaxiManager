package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Payer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfilConducteurController implements Serializable {
    private static final String IMAGE_SAVE_DIR = "Images";
    private static final String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/profile.png";
    public Label dateDebut;
    public Label dateFin;

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label telephoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label villeLabel;

    @FXML
    private Label numeroPermisLabel;

    @FXML
    private Label sexeLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button choisirPhotoButton;

    @FXML
    private void initialize() {
        Conducteur conducteur=null;
        Payer payer=null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Conducteur.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            conducteur = (Conducteur) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();

            FileInputStream fileInputStream1 = new FileInputStream("Payer.txt");
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
            payer = (Payer) objectInputStream1.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }
        catch (Exception e){
            System.out.println("Conducteur non inscris");
        }
        if (payer!=null){
            dateDebut.setText(payer.getDateDebut().toString());
            dateFin.setText(payer.getDateFin().toString());
        }
        if (conducteur != null) {
            String s = conducteur.getPrenom()+conducteur.getIdentifiant_C();
            usernameLabel.setText(s);
            villeLabel.setText(conducteur.getVille());
        } else {
            // Utilisateur par défaut si le conducteur n'est pas disponible
            usernameLabel.setText("DAK");
            villeLabel.setText("Mohammedia");
        }

        // Charger l'image de profil si elle existe
        loadProfileImage();

        // Cercle pour afficher l'image de profil de manière circulaire
        Circle clip = new Circle(profileImageView.getFitWidth() / 2, profileImageView.getFitHeight() / 2, profileImageView.getFitWidth() / 2);
        profileImageView.setClip(clip);
    }

    @FXML
    private void handleChoisirPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(choisirPhotoButton.getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Créer le répertoire de sauvegarde s'il n'existe pas
                File saveDir = new File(IMAGE_SAVE_DIR);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                // Copier le fichier sélectionné dans le répertoire de l'application
                Files.copy(selectedFile.toPath(), new File(IMAGE_SAVE_PATH).toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Charger et afficher l'image
                try (FileInputStream fis = new FileInputStream(IMAGE_SAVE_PATH)) {
                    Image image = new Image(fis);
                    profileImageView.setImage(image);

                    // Appliquer la découpe circulaire à l'image de profil
                    Circle clip = new Circle(profileImageView.getFitWidth() / 2, profileImageView.getFitHeight() / 2, profileImageView.getFitWidth() / 2);
                    profileImageView.setClip(clip);

                    // Créer un objet ImagePattern avec l'image
                    ImagePattern pattern = new ImagePattern(image);

                    // Appliquer le motif à l'objet Circle
                    clip.setFill(pattern);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadProfileImage() {
        File file = new File(IMAGE_SAVE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Image image = new Image(fis);
                profileImageView.setImage(image);

                // Appliquer la découpe circulaire à l'image de profil
                Circle clip = new Circle(profileImageView.getFitWidth() / 2, profileImageView.getFitHeight() / 2, profileImageView.getFitWidth() / 2);
                profileImageView.setClip(clip);

                // Créer un objet ImagePattern avec l'image
                ImagePattern pattern = new ImagePattern(image);

                // Appliquer le motif à l'objet Circle
                clip.setFill(pattern);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ProfilConducteurApp extends Application implements Serializable {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(ProfilConducteurApp.class.getResource("ProfilConducteur-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Profil Conducteur!");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }
}
