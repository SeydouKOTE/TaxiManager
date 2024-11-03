package org.example.frontendtaxi;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class AProposDeNousController {

    @FXML
    private ImageView author1Image;
    @FXML
    private Label author1Name;

    @FXML
    private ImageView author2Image;
    @FXML
    private Label author2Name;

    @FXML
    private ImageView author3Image;
    @FXML
    private Label author3Name;

    @FXML
    private TextFlow aboutUsTextFlow;

    @FXML
    private TextFlow missionTextFlow;

    @FXML
    private TextFlow featuresTextFlow;

    @FXML
    public void initialize() {
        // Set author 1 details
        author1Image.setImage(new Image(getClass().getResourceAsStream("Images/dakio.png.jpg")));
        author1Name.setText("DAKIO Sounsin Isaac Tychique");

        // Set author 2 details
        author2Image.setImage(new Image(getClass().getResourceAsStream("Images/kote.png.jpg")));
        author2Name.setText("KOTE Seydou");

        // Set author 3 details
        author3Image.setImage(new Image(getClass().getResourceAsStream("Images/arsalane.png.jpg")));
        author3Name.setText("ARSALANE Aymane");
        // Set text content
        setTextContent(aboutUsTextFlow, "Bienvenue sur notre plateforme de réservation de taxi, une solution innovante développée pour faciliter les déplacements urbains. Nous sommes une équipe passionnée par la technologie et dédiée à améliorer la mobilité en ville grâce à notre système efficace et convivial.");
        setTextContent(missionTextFlow, "Notre mission est de connecter facilement et rapidement les passagers avec les conducteurs disponibles, en offrant un service de transport fiable, sûr et abordable. Nous comprenons l'importance de la ponctualité et de la sécurité lors des déplacements quotidiens, et nous nous engageons à fournir une expérience utilisateur optimale.");
        setTextContent(featuresTextFlow, "1. Inscription facile : Les conducteurs et les passagers peuvent s'inscrire en quelques étapes simples pour commencer à utiliser notre service.\n2. Recherche de conducteurs : Les passagers peuvent rechercher et contacter des conducteurs disponibles pour leurs trajets dans la ville.\n3. Notification en temps réel : Les conducteurs reçoivent des notifications en temps réel lorsqu'un passager les contacte pour une réservation.\n4. Interface conviviale : Notre application est conçue pour être intuitive et facile à utiliser, même pour les utilisateurs novices.\n5. Sécurité et fiabilité : Nous nous assurons que tous les conducteurs sont vérifiés et que les informations des utilisateurs sont protégées.");
    }

    private void setTextContent(TextFlow textFlow, String content) {
        textFlow.getChildren().clear();
        String[] paragraphs = content.split("\n");
        for (String paragraph : paragraphs) {
            textFlow.getChildren().add(new Text(paragraph + "\n"));
        }
    }

    public static class AProposDeNous extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("AProposDeNous-view.fxml"));
            Scene scene = new Scene(root, 600, 400);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            primaryStage.setTitle("À propos de nous");
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            primaryStage.getIcons().add(icon);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}
