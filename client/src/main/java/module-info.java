module org.clientchatjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.security.jgss;
    requires java.security.sasl;
    requires java.sql;
    requires java.instrument;
    //requires javafx.web;

    /*requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;*/

    exports org.chatapplicationjava.main;
    opens org.chatapplicationjava.main to javafx.fxml;
    opens org.chatapplicationjava.controller to javafx.fxml;
}