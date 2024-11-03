package org.example.frontendtaxi;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class ConnectionAdminController implements Serializable {
    private SceneManager sceneManager;
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + username);
            sceneManager.addScene("AdminApp","Admin-view.fxml");
            sceneManager.switchTo("AdminApp");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password");
        }
    }

    private boolean validateLogin(String username, String password) {
        return "aaaa".equals(username) && "0000".equals(password);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class ConnectionAdminApp extends Application implements Serializable {

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(ConnectionAdminApp.class.getResource("ConnectionAdmin-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Page de Connection!");
            stage.setScene(scene);
            stage.show();
        }
        public static void main(String[] args) {
            launch(args);
        }

    }
}
