package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Passager;
import Metier.Payer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MenuConducteurController implements Initializable, Serializable {
    public Label userId;
    private SceneManager sceneManager;
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    @FXML
    private AnchorPane pane1;
    @FXML
    private AnchorPane pane2;
    private static final String IMAGE_SAVE_DIR = "Images";
    private static final String IMAGE_SAVE_PATH = IMAGE_SAVE_DIR + "/profile2.png";
    @FXML
    private ImageView profil;
    @FXML
    private ImageView menu;
    @FXML
    private BorderPane menuBorderPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane1.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();
        menu.setOnMouseClicked(event -> {
            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });
            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
        AfficheurCentreMenu object = new AfficheurCentreMenu();
        AnchorPane view = object.getPage("MapConducteur-view.fxml");
        menuBorderPane.setCenter(view);
        // Charger l'image de profil si elle existe
        loadProfileImage();

        // Cercle pour afficher l'image de profil de manière circulaire
        Circle clip = new Circle(profil.getFitWidth() / 2, profil.getFitHeight() / 2, profil.getFitWidth() / 2);
        profil.setClip(clip);
        Conducteur conducteur=null;
        try {
            FileInputStream fileInputStream1 = new FileInputStream("Conducteur.txt");
            ObjectInputStream objectInputStream1 = new ObjectInputStream(fileInputStream1);
            conducteur = (Conducteur) objectInputStream1.readObject();
            fileInputStream1.close();
            objectInputStream1.close();
        }
        catch (Exception e){
            System.out.println("erreur pour le paiement");
        }
        if (conducteur!=null){
            String s = conducteur.getPrenom()+conducteur.getIdentifiant_C();
            userId.setText(s);
        }
    }

    @FXML
    public void onProfilButtonClick(ActionEvent event){
        AfficheurCentreMenu object = new AfficheurCentreMenu();
        AnchorPane view = object.getPage("ProfilConducteur-view.fxml");
        menuBorderPane.setCenter(view);
    }
    @FXML
    public void onAProposDeNousButtonClick(ActionEvent event){
        AfficheurCentreMenu object = new AfficheurCentreMenu();
        AnchorPane view = object.getPage("AProposDeNous-view.fxml");
        menuBorderPane.setCenter(view);
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, content);
        alert.setTitle(title);
        alert.showAndWait();
    }
    @FXML
    public void onChangeLeModeButtonClick(ActionEvent event){
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
            sceneManager.addScene("MenuPassagerApp","MenuPassager-view.fxml");
            sceneManager.switchTo("MenuPassagerApp");
        } else {
            sceneManager.addScene("InscriptionPassagerApp","InscriptionPassager-view.fxml");
            sceneManager.switchTo("InscriptionPassagerApp");
        }
    }
    @FXML
    public void onAccueilButtonClick(ActionEvent event){
        AfficheurCentreMenu object = new AfficheurCentreMenu();
        AnchorPane view = object.getPage("MapConducteur-view.fxml");
        menuBorderPane.setCenter(view);
    }

    @FXML
    public void onQuitterButtonClick(){ System.exit(0);}
    private void loadProfileImage() {
        File file = new File(IMAGE_SAVE_PATH);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                Image image = new Image(fis);
                profil.setImage(image);

                // Appliquer la découpe circulaire à l'image de profil
                Circle clip = new Circle(profil.getFitWidth() / 2, profil.getFitHeight() / 2, profil.getFitWidth() / 2);
                profil.setClip(clip);

                // Créer un objet ImagePattern avec l'image
                ImagePattern pattern = new ImagePattern(image);

                // Appliquer le motif à l'objet Circle
                clip.setFill(pattern);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class MenuConducteurApp extends Application implements Serializable {
        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("MenuConducteur-view.fxml"));
            primaryStage.setScene(new Scene(root, 700, 440));
            primaryStage.setTitle("Conducteur!");
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKblancpetit.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }
}


