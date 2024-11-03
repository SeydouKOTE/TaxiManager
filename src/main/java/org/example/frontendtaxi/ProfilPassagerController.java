package org.example.frontendtaxi;

import Metier.Passager;
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

public class ProfilPassagerController implements Serializable {
    private static final String IMAGE_SAVE_DIR = "Images";
    private static final String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/profile2.png";

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
    private Label usernameLabel;
    @FXML
    private Label dateDebut;
    @FXML
    private Label dateFin;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button choisirPhotoButton;
    @FXML
    private void initialize() {
        Passager passager=null;
        try {
            FileInputStream fileInputStream = new FileInputStream("Passager.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            passager = (Passager) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }catch (Exception e){
            System.out.println("Passager non inscris");
        }
        if (passager != null) {
            String s = passager.getPrenom()+passager.getIdendifiant_P();
            usernameLabel.setText(s);
            villeLabel.setText(passager.getVille());
        } else {
            // Utilisateur par défaut si le passager n'est pas disponible
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

    public static class ProfilPassagerApp extends Application implements Serializable {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(ProfilPassagerApp.class.getResource("ProfilPassager-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Profil Passager!");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }
}
