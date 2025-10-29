module org.chatapplicationjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires java.rmi;
    requires javafx.graphics;
    requires java.naming;
    //requires javafx.web;

    /*requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;*/

    opens org.chatapplicationjava.view to javafx.fxml;
    exports org.chatapplicationjava.controller.inter;
    exports org.chatapplicationjava.service.inter;
    exports org.chatapplicationjava.model;
    exports org.chatapplicationjava.network;
    opens org.chatapplicationjava.controller to javafx.fxml;
    exports org.chatapplicationjava.main;
    exports org.chatapplicationjava.api;
}