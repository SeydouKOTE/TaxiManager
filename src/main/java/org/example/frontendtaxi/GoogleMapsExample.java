package org.example.frontendtaxi;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GoogleMapsExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        // Charger le fichier HTML contenant la carte Google Maps
        webEngine.load(getClass().getResource("map.html").toExternalForm());

        Scene scene = new Scene(webView, 800, 600);
        primaryStage.setTitle("Google Maps in JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
