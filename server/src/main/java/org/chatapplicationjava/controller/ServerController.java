package org.chatapplicationjava.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

public class ServerController {

    @FXML
    private ToggleButton toggleButton;

    @FXML
    private TextArea Text_area;

    @FXML
    public void initialize() {
        if (toggleButton != null && Text_area != null) {

            Text_area.setEditable(false);//Bloquer l'utilisateur de modifier le texte
            toggleButton.getStyleClass().add("custom-toggle-button");

            //Changer on à off et vise versa
            toggleButton.selectedProperty().addListener((obs, oldVal, newVal) -> {
                toggleButton.setText(newVal ? "On" : "Off");

                if (newVal) {
                    //TO DO appel traitement main
                    Text_area.setText("Button is ON");
                } else {
                    Text_area.setText("Server is OFF");
                }
            });
        }
    }
}
