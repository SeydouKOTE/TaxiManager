package org.example.frontendtaxi;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.Serializable;
import java.net.URL;

public class AfficheurCentreMenu implements Serializable {
    private AnchorPane view;
    public AnchorPane getPage(String fileName){
        try {
            URL fileUrl = MenuPassagerController.MenuPassagerApp.class.getResource(fileName);
            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file can't be found");
            }
            view = new FXMLLoader().load(fileUrl);
        }catch (Exception e){
            System.out.println("Pas de "+fileName+", faut verifier le nom du fichier");
        }
        return view;
    }
}
