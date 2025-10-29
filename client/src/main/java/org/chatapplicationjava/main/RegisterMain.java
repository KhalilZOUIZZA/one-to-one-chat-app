package org.chatapplicationjava.main;
    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.scene.image.Image;
    import javafx.stage.Stage;
    import java.io.IOException;

public class RegisterMain  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientChatApplication.class.getResource("/org/chatapplicationjava/view/Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Image icon = new Image(getClass().getResourceAsStream("/org/chatapplicationjava/view/AppLogo.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Register");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
