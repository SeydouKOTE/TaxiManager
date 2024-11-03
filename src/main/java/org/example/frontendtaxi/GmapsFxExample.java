package org.example.frontendtaxi;

import com.dlsc.gmapsfx.GoogleMapView;
import com.dlsc.gmapsfx.javascript.object.GoogleMap;
import com.dlsc.gmapsfx.javascript.object.LatLong;
import com.dlsc.gmapsfx.javascript.object.MapOptions;
import com.dlsc.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GmapsFxExample extends Application {

    private GoogleMapView mapView;
    private GoogleMap map;

    @Override
    public void start(Stage stage) {
        // Initialisez GoogleMapView avec votre clé API
        mapView = new GoogleMapView(null,"AIzaSyDo3UPGE7u9-NUMSkv3er161Gfo7PqiES0");
        mapView.addMapInitializedListener(() -> {
            MapOptions mapOptions = new MapOptions();
            mapOptions.center(new LatLong(47.606189, -122.335842))
                    .mapType(MapTypeIdEnum.SATELLITE)
                    .zoom(12);
            map = mapView.createMap(mapOptions);
        });

        Scene scene = new Scene(mapView, 800, 600);
        stage.setTitle("GmapsFx Example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
