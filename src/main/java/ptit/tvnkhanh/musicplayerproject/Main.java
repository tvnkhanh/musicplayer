package ptit.tvnkhanh.musicplayerproject;

import java.sql.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Pages/LogInSignUpPage/LogInPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/LogInSignUpPage/LogInSignUp.css").toExternalForm());
        stage.setTitle("Spotify");
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Images/spotify.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();
    }
}