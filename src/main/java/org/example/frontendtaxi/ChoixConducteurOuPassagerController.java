package org.example.frontendtaxi;

import Metier.Conducteur;
import Metier.Passager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

public class ChoixConducteurOuPassagerController implements Serializable {
    private SceneManager sceneManager;
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    public void siChoixConducteur(ActionEvent actionEvent) {
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
            sceneManager.addScene("MenuConducteurApp", "MenuConducteur-view.fxml");
            sceneManager.switchTo("MenuConducteurApp");
        } else {
            sceneManager.addScene("InscriptionConducteurApp", "InscriptionConducteur-view.fxml");
            sceneManager.switchTo("InscriptionConducteurApp");
        }
    }

    public void siChoixPassager(ActionEvent actionEvent) {
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
            sceneManager.addScene("MenuPassagerApp", "MenuPassager-view.fxml");
            sceneManager.switchTo("MenuPassagerApp");
        } else {
            sceneManager.addScene("InscriptionPassagerApp", "InscriptionPassager-view.fxml");
            sceneManager.switchTo("InscriptionPassagerApp");
        }
    }

    public static class ChoixConducteurOuPassagerApp extends Application implements Serializable {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(ChoixConducteurOuPassagerApp.class.getResource("ChoixConducteurOuPassager-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 700, 400);
            Image icon = new Image(getClass().getResourceAsStream("Images/DAKpetit.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Conducteur ou Passager!");
            stage.setScene(scene);
            stage.show();
        }
    }
}
