module org.example.frontendtaxi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.xml.bind;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.swing;
    requires com.dlsc.gmapsfx;
    requires jdk.jsobject;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.rmi;
    opens Metier to org.hibernate.orm.core, javafx.base;

    opens org.example.frontendtaxi to javafx.fxml;
    exports org.example.frontendtaxi;
    exports Metier.Serveur;
    opens Metier.Serveur to javafx.fxml;

}