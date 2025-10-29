package org.chatapplicationjava.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import org.chatapplicationjava.model.Account;
import org.chatapplicationjava.model.Discussion;
import org.chatapplicationjava.model.Message;
import org.chatapplicationjava.model.User;
import javafx.scene.input.KeyCode;
import org.chatapplicationjava.service.impl.UserServiceImpl;
import org.chatapplicationjava.service.inter.UserService;
import org.chatapplicationjava.util.ClientContext;
import javafx.application.Platform;
import org.chatapplicationjava.util.ObjectConverter;


public class HomePageController implements Initializable {

	//ArrayList<User> users = new ArrayList<>();
	ArrayList<Discussion> Disc = new ArrayList<>();
	ArrayList<Message> messagesA = new ArrayList<>();
	//User me = new User("khalil", "zouizza", new Account("khalil@gmail.com"));
	User me = (User) ClientContext.lookup("user");

	private boolean status = true;
	private TitledPane titledPane;
	private boolean side = true;
	private UserService userService;
	public String oldMail; 

	@FXML
	private TextField searchContactInput;
	
	@FXML
	private Pane rootPane;

	@FXML
	private VBox contactChildren;

	@FXML
	private VBox RequestsChildren;

	@FXML
	private VBox addContactChildren;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ClientContext.bind("homePageController",this);

		userService = new UserServiceImpl();
		/*User user1 = new User("yassine", "chalati", new Account("yassine@gmail.com"));
		Discussion dis = new Discussion(me, user1);

		messagesA.add(new Message(me, "sended message 1", new Date(), true, true, true, dis));
		messagesA.add(new Message(user1, "sended message 2", new Date(), true, true, true, dis));
		messagesA.add(new Message(me, "sended message 3", new Date(), true, true, true, dis));
		messagesA.add(new Message(user1, "sended message 4", new Date(), true, true, true, dis));
		dis.setMessages(messagesA);
		Disc.add(dis);
		
		//User user1 = new User("yassine", "chalati", new Account("yassine@gmail.com"));
		user1.getDiscussions().add(dis);
		
		me.getContacts().add(user1);
		me.getContacts().add(new User("mehdi", "ouballa", new Account("mehdi@gmail.com")));
		me.getContacts().add(new User("marouane", "ait kika", new Account("marouane@gmail.com")));*/

		
		//users.add(new User("me", "me", new Account("drissi@gmail.com")));

		//System.out.println(dis.getMessages().get(0).getText());
		
		
		
		//me.setDiscussions(Disc);

		
		for (User user : me.getContacts()) {
			AnchorPane contactAchorPane = createContactAnchorPane(user);
			contactChildren.getChildren().add(contactAchorPane);
			//thread -> anchorpan
		}


		for(User user : me.getInviters()){
			AnchorPane requestsAnchorPane = createRequestsAnchorPane(user);
			RequestsChildren.getChildren().add(requestsAnchorPane);
		}


	}
	
	public void refreshUser(User user) {
	    Platform.runLater(() -> {  
	        System.out.println("1111111");
	        if(user.getAccount().getEmail().equals(oldMail)) {
	        	//TitledPane oldTitledPane = findTitledChatPaneToReplace();
		        if (titledPane != null) {
		            rootPane.getChildren().remove(titledPane);
			        System.out.println("222222");
		        }
		        //TitledPane newTitledPane = createChatTitledPane(user);
				titledPane = createChatTitledPane(user);
		        rootPane.getChildren().add(titledPane);
		        System.out.println("3333333");
	        }
	    });
	}
	
	@FXML
    private void handleSearch(ActionEvent event) {
    	String emailInputText = searchContactInput.getText();
		addContactChildren.getChildren().clear();

		if(!emailInputText.isEmpty()){
			for (User user:userService.searchContacts(emailInputText)) {
				AnchorPane addContactAnchorPane = createAddContactAnchorPane(user, new AtomicBoolean(false));
				addContactChildren.getChildren().add(addContactAnchorPane);
			}
		}
	}

	public AnchorPane createContactAnchorPane(User user) { //contact
		
		AnchorPane anchorPane = new AnchorPane();
        //anchorPane.setStyle("-fx-background-color: #b0c4de;");

		ImageView imageView = new ImageView(new Image(
				HomePageController.class.getResource("/org/chatapplicationjava/assets/contact.png").toString()));
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		imageView.setLayoutX(5);
		imageView.setLayoutY(7);

		Label label = new Label(user.getLastName() + " " + user.getFirstName());
		label.setLayoutX(66);
		label.setLayoutY(25);

		Button button = new Button();
		button.setOpacity(0.1);
		button.setPrefHeight(65);
		button.setPrefWidth(300);
		button.setOnAction(event -> {
			//TitledPane oldTitledPane = findTitledChatPaneToReplace();
			if (titledPane != null) {
				rootPane.getChildren().remove(titledPane);
			}
			titledPane = createChatTitledPane(user);
			rootPane.getChildren().add(titledPane);
		});

		Circle circle = new Circle();
		circle.setFill(status ? Color.GREEN : Color.GRAY);
		circle.setLayoutX(45.0);
		circle.setLayoutY(50.0);
		circle.setRadius(7.0);
		circle.setStrokeWidth(2);
		circle.setStroke(Color.WHITE);
		circle.setStrokeType(StrokeType.INSIDE);
		
		Label labelNotification = new Label("New Messages");
		labelNotification.setLayoutX(215.0);
		labelNotification.setLayoutY(25.0);
		labelNotification.setTextFill(Color.RED);

		anchorPane.getChildren().addAll(imageView, label, button, circle,labelNotification);
		anchorPane.setPrefHeight(65);
		anchorPane.setPrefWidth(200);

		return anchorPane;
	}

	private AnchorPane createAddContactAnchorPane(User user, AtomicBoolean invited) {
		AnchorPane anchorPane = new AnchorPane();

		ImageView imageView = new ImageView(new Image(
				HomePageController.class.getResource("/org/chatapplicationjava/assets/contact.png").toString()));
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		imageView.setLayoutX(5);
		imageView.setLayoutY(7);

		Label label = new Label(user.getLastName() + " " + user.getFirstName());
		label.setLayoutX(66);
		label.setLayoutY(25);

		Button button = new Button(invited.get() ? "Cancel" : "Invit");
		button.setOpacity(1);
		button.setLayoutX(219);
		button.setLayoutY(20);
		button.setPrefWidth(60);
		button.setOnAction(event -> {

			boolean invitResult = userService.invitUser(user);

			//invited.set(!invited.get());
			button.setText(invitResult ? "Cancel" : "Invit");
		});

		anchorPane.getChildren().addAll(imageView, label, button);
		anchorPane.setPrefHeight(65);
		anchorPane.setPrefWidth(200);

		return anchorPane;
	}

	private AnchorPane
	createRequestsAnchorPane(User user) {
		AnchorPane anchorPane = new AnchorPane();

		ImageView imageView = new ImageView(new Image(
				HomePageController.class.getResource("/org/chatapplicationjava/assets/contact.png").toString()));
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		imageView.setLayoutX(5);
		imageView.setLayoutY(7);

		Label label = new Label(user.getLastName() + " " + user.getFirstName());
		label.setLayoutX(66);
		label.setLayoutY(25);

		Button buttonAccept = new Button("Accept");
		buttonAccept.setOpacity(1);
		buttonAccept.setLayoutX(170);
		buttonAccept.setLayoutY(17);
		buttonAccept.setPrefWidth(60);
		buttonAccept.setOnAction(event -> {
			boolean acceptResult = userService.acceptUser(user);
			if(acceptResult){
				me = ((User) ClientContext.lookup("user"));
				me.getContacts().add(user);
				me.getInviters().remove(user);

				RequestsChildren.getChildren().remove(anchorPane);

				AnchorPane contactAchorPane = createContactAnchorPane(user);
				contactChildren.getChildren().add(contactAchorPane);
			}
		});

		Button buttonRefuse = new Button("Refuse");
		buttonRefuse.setOpacity(1);
		buttonRefuse.setLayoutX(234);
		buttonRefuse.setLayoutY(17);
		buttonRefuse.setPrefWidth(60);
		buttonRefuse.setOnAction(event -> {
			boolean refuseResult = userService.refuseUser(user);
			if(refuseResult){
				me = ((User) ClientContext.lookup("user"));
				me.getInviters().remove(user);
				RequestsChildren.getChildren().remove(anchorPane);
			}
		});

		anchorPane.getChildren().addAll(imageView, label, buttonAccept, buttonRefuse);
		anchorPane.setPrefHeight(65);
		anchorPane.setPrefWidth(200);

		return anchorPane;
	}

	public TitledPane createChatTitledPane(User user) {
		oldMail = user.getAccount().getEmail();
		TitledPane titledPane = new TitledPane();
		titledPane.setAnimated(false);
		titledPane.setLayoutX(323.0);
		titledPane.setLayoutY(-47.0);
		titledPane.setPrefHeight(547.0);
		titledPane.setPrefWidth(673.0);
		titledPane.setText(user.getLastName());

		AnchorPane contentPane = new AnchorPane();
		contentPane.setMinHeight(0.0);
		contentPane.setMinWidth(0.0);
		contentPane.setPrefHeight(180.0);
		contentPane.setPrefWidth(200.0);

		ToolBar toolBar = new ToolBar();
		toolBar.setLayoutY(20.0);
		toolBar.setPrefHeight(46.0);
		toolBar.setPrefWidth(673.0);

		ImageView imageView = new ImageView(new Image(
				HomePageController.class.getResource("/org/chatapplicationjava/assets/contact.png").toString()));
		imageView.setFitHeight(35.0);
		imageView.setFitWidth(35.0);

		Label label = new Label(user.getLastName());
		label.setPrefHeight(21.0);
		label.setPrefWidth(87.0);

		toolBar.getItems().addAll(imageView, label);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setLayoutX(-1.0);
		scrollPane.setLayoutY(66.0);
		scrollPane.setPrefHeight(393.0);
		scrollPane.setPrefWidth(673.0);

		Pane innerPane = new Pane();
		innerPane.setPrefWidth(630);

		VBox messages = new VBox();

		innerPane.getChildren().add(messages);

		scrollPane.setContent(innerPane);

		TextField textField = new TextField();
		textField.setLayoutY(459.0);
		textField.setPrefHeight(57.0);
		textField.setPrefWidth(603.0);

		ImageView searchIcon = new ImageView(new Image(
				HomePageController.class.getResource("/org/chatapplicationjava/assets/send-icon.png").toString()));
		searchIcon.setFitHeight(40.0);
		searchIcon.setFitWidth(40.0);
		searchIcon.setLayoutX(618.0);
		searchIcon.setLayoutY(468.0);
/*
		scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.doubleValue() == 0.0) {
				for (int i = 0; i < 5; i++) {
					System.out.println("Scrolled to the top!");

					Pane messageInnerPane = new Pane();
					messageInnerPane.setPrefWidth(672.0);

					Label innerLabel = createWrappedLabel("Loaded message " + i);
					innerLabel.setPrefWidth(200.0);
					innerLabel.setLayoutX(455);
					innerLabel.setAlignment(Pos.TOP_RIGHT);
					innerLabel.setStyle("-fx-background-color: lightblue;");

					Pane emptySpace = new Pane();
					emptySpace.setPrefHeight(20);

					messageInnerPane.getChildren().addAll(innerLabel);
					messages.getChildren().add(0, messageInnerPane);
					messages.getChildren().add(1, emptySpace);
				}
			}
		});

		scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.doubleValue() == 1.0) {
				System.out.println("Scrolled to the bottom!");
			}
		});
*/
		Button button = new Button();
		button.setLayoutX(605.0);
		button.setLayoutY(460.0);
		button.setMnemonicParsing(false);
		button.setOpacity(0.16);
		button.setPrefHeight(55.0);
		button.setPrefWidth(63.0);
		button.setOnAction(event -> {
			String enteredText = textField.getText();

			if (!enteredText.isEmpty()) {
				System.out.println("probelme");
				Message message = userService.sendMessage(new Message(new User(me.getFirstName(),me.getLastName(),me.getAccount()),enteredText,new Discussion(new User(user.getDiscussions().get(0).getFirstUser().getAccount()),new User(user.getDiscussions().get(0).getSecondUser().getAccount()))));

				System.out.println("size : " + ObjectConverter.objectToByteArray(message).length);
				if(message != null){
					user.getDiscussions().get(0).getMessages().add(message);
					System.out.println(message.getText());
					HBox hBox = new HBox();
					hBox.setAlignment(Pos.CENTER_RIGHT);
					hBox.setPadding(new Insets(5, 15, 5, 430));
					Label label1 = createWrappedLabel(enteredText);
					label1.setPrefWidth(200.0);
					TextFlow textFlow = new TextFlow(label1);
					textFlow.setStyle(
							"-fx-color: rgb(239,242,255); " +
									"-fx-background-color: rgb(15,125,242); " +
									"-fx-background-radius: 20px;"
					);

					textFlow.setPadding(new Insets(5, 10, 5, 10));
					label1.setTextFill(Color.color(0.934,0.945,0.996));
					hBox.getChildren().add(textFlow);
					messages.getChildren().add(hBox);
				}else{
					HBox hBox = new HBox();
					hBox.setAlignment(Pos.CENTER_RIGHT);
					hBox.setPadding(new Insets(5, 15, 5, 430));
					Label label1 = createWrappedLabel(enteredText);
					label1.setPrefWidth(200.0);
					TextFlow textFlow = new TextFlow(label1);
					textFlow.setStyle(
							"-fx-color: rgb(239,242,255); " +
									"-fx-background-color: rgba(242,87,15,0.94); " +
									"-fx-background-radius: 20px;"
					);

					textFlow.setPadding(new Insets(5, 10, 5, 10));
					label1.setTextFill(Color.color(0.934,0.945,0.996));
					hBox.getChildren().add(textFlow);
					messages.getChildren().add(hBox);
				}

				
				textField.clear();
				textField.requestFocus();
				Platform.runLater(() -> {
				    scrollPane.setVvalue(1.0);
				});
			}
		});

		textField.setOnKeyPressed(event -> {
			String enteredText = textField.getText();
			if (event.getCode() == KeyCode.ENTER) {

				if (!enteredText.isEmpty()) {
					Message message = userService.sendMessage(new Message(new User(me.getFirstName(),me.getLastName(),me.getAccount()),enteredText,new Discussion(new User(user.getDiscussions().get(0).getFirstUser().getAccount()),new User(user.getDiscussions().get(0).getSecondUser().getAccount()))));
					//Message message = new Message(new User(new Account("testRam@email.com")),"TestRam",new Discussion(new User(new Account("testRam1")), new User(new Account("testRam2"))));
					user.getDiscussions().get(0).getMessages().add(message);
					if(message != null){
						HBox hBox = new HBox();
						hBox.setAlignment(Pos.CENTER_RIGHT);
						hBox.setPadding(new Insets(5, 15, 5, 430));
						Label label1 = createWrappedLabel(enteredText);
						label1.setPrefWidth(200.0);
						TextFlow textFlow = new TextFlow(label1);
						textFlow.setStyle(
								"-fx-color: rgb(239,242,255); " +
										"-fx-background-color: rgb(15,125,242); " +
										"-fx-background-radius: 20px;"
						);

						textFlow.setPadding(new Insets(5, 10, 5, 10));
						label1.setTextFill(Color.color(0.934,0.945,0.996));
						hBox.getChildren().add(textFlow);
						messages.getChildren().add(hBox);
					}else{
						HBox hBox = new HBox();
						hBox.setAlignment(Pos.CENTER_RIGHT);
						hBox.setPadding(new Insets(5, 15, 5, 430));
						Label label1 = createWrappedLabel(enteredText);
						label1.setPrefWidth(200.0);
						TextFlow textFlow = new TextFlow(label1);
						textFlow.setStyle(
								"-fx-color: rgb(239,242,255); " +
										"-fx-background-color: rgba(242,87,15,0.94); " +
										"-fx-background-radius: 20px;"
						);

						textFlow.setPadding(new Insets(5, 10, 5, 10));
						label1.setTextFill(Color.color(0.934,0.945,0.996));
						hBox.getChildren().add(textFlow);
						messages.getChildren().add(hBox);
					}

					
					textField.clear();
					textField.requestFocus();
					Platform.runLater(() -> {
					    scrollPane.setVvalue(1.0);
					});
				}
			}
		});

		if(!user.getDiscussions().isEmpty()) {
			for (Message message : user.getDiscussions().get(0).getMessages()) {
				if(!message.getText().isEmpty()) {
					if (me.getAccount().getEmail().equals(message.getSenderUser().getAccount().getEmail())) {
						HBox hBox = new HBox();
						hBox.setAlignment(Pos.CENTER_RIGHT);
						hBox.setPadding(new Insets(5, 15, 5, 430));
						Label label1 = createWrappedLabel(message.getText());
						label1.setPrefWidth(200.0);
						TextFlow textFlow = new TextFlow(label1);
						textFlow.setStyle(
								"-fx-color: rgb(239,242,255); " +
										"-fx-background-color: rgb(15,125,242); " +
										"-fx-background-radius: 20px;"
						);

						textFlow.setPadding(new Insets(5, 10, 5, 10));
						label1.setTextFill(Color.color(0.934, 0.945, 0.996));
						hBox.getChildren().add(textFlow);
						messages.getChildren().add(hBox);
					
					} else {
						HBox hBox = new HBox();
						hBox.setAlignment(Pos.CENTER_LEFT);
						hBox.setPadding(new Insets(5, 15, 5, 10));
						Label label1 = createWrappedLabel(message.getText());
						label1.setPrefWidth(200.0);
						TextFlow textFlow = new TextFlow(label1);
						textFlow.setStyle(
								"-fx-color: rgb(239,242,255); " +
										"-fx-background-color: rgb(15,125,242); " +
										"-fx-background-radius: 20px;"
						);

						textFlow.setPadding(new Insets(5, 10, 5, 10));
						label1.setTextFill(Color.color(0.934, 0.945, 0.996));
						hBox.getChildren().add(textFlow);
						messages.getChildren().add(hBox);
					}

				}
			}
		}
		
        //messagesA.clear();

		Circle circle = new Circle();
		circle.setFill(status ? Color.GREEN : Color.GRAY);
		circle.setLayoutX(37.0);
		circle.setLayoutY(55.0);
		circle.setRadius(7.0);
		circle.setStrokeWidth(2);
		circle.setStroke(Color.WHITE);
		circle.setStrokeType(StrokeType.INSIDE);

		contentPane.getChildren().addAll(toolBar, circle, scrollPane, textField, searchIcon, button);
		titledPane.setContent(contentPane);

		Platform.runLater(() -> {
		    scrollPane.setVvalue(1.0);
		});
		
		return titledPane;
	}

	private TitledPane findTitledChatPaneToReplace() {
		if (rootPane.getChildren().size() > 0 && rootPane.getChildren().get(0) instanceof TitledPane) {
			return (TitledPane) rootPane.getChildren().get(0);
		}
		return null;
	}

	private Label createWrappedLabel(String text) {
		Label label = new Label();

		int wrapLength = 40;
		StringBuilder wrappedText = new StringBuilder();
		String[] words = text.split("\\s+");

		int currentLineLength = 0;

		for (String word : words) {
			if (text.length() > wrapLength) {
				wrappedText.append("\n");
				currentLineLength = 0;
			}
			wrappedText.append(word).append(" ");
			currentLineLength += word.length() + 1;
		}

		label.setText(text);
		label.setWrapText(true);

		return label;
	}

	public void createReceivedMsg(VBox vbox,String message){
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(5, 15, 5, 10));
		Label label1 = createWrappedLabel(message);
		label1.setPrefWidth(200.0);
		TextFlow textFlow = new TextFlow(label1);
		textFlow.setStyle(
				"-fx-color: rgb(239,242,255); " +
						"-fx-background-color: rgb(15,125,242); " +
						"-fx-background-radius: 20px;"
		);

		textFlow.setPadding(new Insets(5, 10, 5, 10));
		label1.setTextFill(Color.color(0.934,0.945,0.996));
		hBox.getChildren().add(textFlow);
		vbox.getChildren().add(hBox);
	}


}
