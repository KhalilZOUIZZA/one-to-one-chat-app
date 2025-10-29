package org.chatapplicationjava.controller;

import javafx.event.ActionEvent;


import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.chatapplicationjava.concurrency.ListnerSocket;
import org.chatapplicationjava.model.Authentication;
import org.chatapplicationjava.model.User;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.service.inter.UserService;

import javafx.scene.paint.Color;
import org.chatapplicationjava.util.ClientContext;


public class LoginController implements Initializable{
	
    private Label statusLabel;
    private UserService userService;
    
    @FXML
	private AnchorPane rootAnchorPane;
    

    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
	    statusLabel = createStatusLabel("", 361.0, 173.0);
    	rootAnchorPane.getChildren().add(statusLabel);
        userService = new UserServiceImpl();
	}

	@FXML
    private TextField emailInput;
	
	@FXML
    private TextField passwordInput;
	
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
    	
    	String emailInputText = emailInput.getText();
        String passwordInputText = passwordInput.getText();
        
        if(!emailInputText.isEmpty() && !passwordInputText.isEmpty()) {

        	if(userService.authenticate(new Authentication(emailInputText, passwordInputText))) {
                new Thread(new ListnerSocket()).start();
                //User user = (User) ClientContext.lookup("user");
        		//System.out.println("Authenticated");
                //System.out.println(user.getFirstName() + " " + user.getLastName() + " " + user.getAccount().getEmail());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/chatapplicationjava/view/HomePage.fxml"));
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
                stage.centerOnScreen();

                stage.show();
        	}
        	else {
                System.out.println("ERROR");
        	}
            statusLabel.setText("good");

            
        }else {
            statusLabel.setText("bad");

        }
        
        ////////
    	/*

        
        */
    }
    
    @FXML
    private void handleBackToRegisterButtonAction(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/chatapplicationjava/view/Register.fxml"));
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