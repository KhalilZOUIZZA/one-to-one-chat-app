package org.chatapplicationjava.controller;

import javafx.event.ActionEvent;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import org.chatapplicationjava.model.Account;
import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.service.inter.UserService;


public class RegisterController implements Initializable{
	
    private Label statusLabel;
    private UserService userService;
    
    @FXML
	private AnchorPane rootAnchorPane;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
        userService = new UserServiceImpl();
	    statusLabel = createStatusLabel("", 360.0, 275.0);
    	rootAnchorPane.getChildren().add(statusLabel); 
	}
	@FXML
    private TextField nomInput;
    
	@FXML
    private TextField prenomInput;
	
	@FXML
    private TextField emailInput;
	
	@FXML
    private TextField passwordInput;

    
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
    	
        String nomInputText = nomInput.getText();
        String prenomInputText = prenomInput.getText();
        String emailInputText = emailInput.getText();
        String passwordInputText = passwordInput.getText();
        
        if(!emailInputText.isEmpty() && !passwordInputText.isEmpty() && !nomInputText.isEmpty() && !prenomInputText.isEmpty()) {

            boolean result = userService.register(new Authentication(emailInputText,passwordInputText), new User(prenomInputText,nomInputText,new Account(emailInputText)));

            if(result) {
                statusLabel.setText("Account created");
                nomInput.setText("");
                prenomInput.setText("");
                emailInput.setText("");
                passwordInput.setText("");
            }
            else{
                statusLabel.setText("Account is not created");

            }
        }else {
            statusLabel.setText("All fields are required");
        }

    	///////////////
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/chatapplicationjava/view/Login.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void handleOpenLoginButtonAction(ActionEvent event) throws IOException {

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/chatapplicationjava/view/Login.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    private Label createStatusLabel(String text, double layoutX, double layoutY) {
        Label statusLabel = new Label(text);
        statusLabel.setLayoutX(layoutX);
        statusLabel.setLayoutY(layoutY);
        statusLabel.setPrefWidth(155.0);
        statusLabel.setPrefHeight(21.0);
        statusLabel.setTextFill(Color.web("#f80000"));

        return statusLabel;
    }
}
