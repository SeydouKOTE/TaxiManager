package org.example.frontendtaxi;

import Metier.Serveur.ServeurObjet;
import Metier.Serveur.TaxiServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.Serializable;

public class AApplication1 extends Application implements Serializable {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Admin-view.fxml"));
        primaryStage.setScene(new Scene(root, 700, 400));
        Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Gestion des Conducteurs et Passagers");
        primaryStage.show();

        // Start the TCP server in a new thread
        new Thread(() -> {
            try {
                new TaxiServer().startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                ServeurObjet.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
