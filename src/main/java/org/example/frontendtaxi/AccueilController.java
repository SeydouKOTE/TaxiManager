package org.example.frontendtaxi;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;

public class AccueilController implements Serializable{
    @FXML
    private AnchorPane accueil;
    @FXML
    private ImageView logoTAxi1;
    @FXML
    private ImageView logoTAxi11;
    @FXML
    private ImageView logoTAxi111;
    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void onDragEntrer(MouseEvent mouseEvent) throws IOException {
        // Create the animation for each image
        Timeline animation1 = createAnimation(logoTAxi111, 0);
        Timeline animation2 = createAnimation(logoTAxi11, 0.3);
        Timeline animation3 = createAnimation(logoTAxi1, 0.6);

        // Create a sequential transition to play animations one after another
        SequentialTransition sequentialTransition = new SequentialTransition(animation1, animation2, animation3);

        // Set the action to perform after the animation finishes
        sequentialTransition.setOnFinished(event -> {
            sceneManager.addScene("ChoixConducteurOuPassagerApp", "ChoixConducteurOuPassager-view.fxml");
            sceneManager.switchTo("ChoixConducteurOuPassagerApp");
        });

        // Play the animation
        sequentialTransition.play();
    }

    private Timeline createAnimation(ImageView imageView, double order) {
        // Calculate the duration and delay based on the order of the image
        Duration duration = Duration.seconds(0.70);
        Duration delay = Duration.seconds(0);

        // Create a timeline for the animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageView.translateXProperty(), 0)),
                new KeyFrame(duration, new KeyValue(imageView.translateXProperty(), 700)) // Assuming the images move across the whole width
        );

        // Add delay to the animation
        timeline.setDelay(delay);

        return timeline;
    }

    public static class AccueilApp extends Application implements Serializable {

        public static void main(String[] args) throws InterruptedException {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(AccueilApp.class.getResource("Accueil-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Accueil Principal!");
            stage.setScene(scene);
            stage.show();
        }
    }
}
