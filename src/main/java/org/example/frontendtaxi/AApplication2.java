package org.example.frontendtaxi;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.Serializable;

public class AApplication2 extends Application implements Serializable{

    @Override
    public void start(Stage primaryStage) {
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.addScene("AccueilApp", "Accueil-view.fxml");
        sceneManager.addScene("ConnectionAdminApp", "ConnectionAdmin-view.fxml");
        Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
        primaryStage.getIcons().add(icon);

        sceneManager.switchTo("AccueilApp");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

