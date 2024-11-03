package org.example.frontendtaxi;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SceneManager implements Serializable {

    private Stage primaryStage;
    private Map<String, Scene> scenes = new HashMap<>();

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }

    public void addScene(String name, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root, 700, 500);
            scenes.put(name, scene);

            // Pass SceneManager to the controller
            Object controller = loader.getController();
            if (controller instanceof AccueilController) {
                ((AccueilController) controller).setSceneManager(this);
            } else if (controller instanceof ChoixConducteurOuPassagerController) {
                ((ChoixConducteurOuPassagerController) controller).setSceneManager(this);
            } else if (controller instanceof InscriptionConducteurController) {
                ((InscriptionConducteurController) controller).setSceneManager(this);
            } else if (controller instanceof InscriptionPassagerController) {
                ((InscriptionPassagerController) controller).setSceneManager(this);
            } else if (controller instanceof ProfilPassagerController) {
                ((ProfilPassagerController) controller).setSceneManager(this);
            } else if (controller instanceof ProfilConducteurController) {
                ((ProfilConducteurController) controller).setSceneManager(this);
            } else if (controller instanceof MenuPassagerController) {
                ((MenuPassagerController) controller).setSceneManager(this);
            } else if (controller instanceof ConnectionAdminController) {
                ((ConnectionAdminController) controller).setSceneManager(this);
            } else if (controller instanceof AdminController) {
                ((AdminController) controller).setSceneManager(this);
            } else if (controller instanceof MenuConducteurController) {
                ((MenuConducteurController) controller).setSceneManager(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void switchTo(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }
}

