package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ptit.tvnkhanh.musicplayerproject.Main;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private double prefWidthApp = 1510;
    private double prefHeightApp = 750;
    private double prefHeightPlayer = 102;

    public Controller() {
    }

    public double getPrefWidthApp() {
        return prefWidthApp;
    }

    public double getPrefHeightApp() {
        return prefHeightApp;
    }

    public double getPrefHeightPlayer() {
        return prefHeightPlayer;
    }

    //    Switch Page Method:
    public void switchToHomePage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/HomePage/HomePage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/HomePage/HomePage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSearchPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/SearchPage/SearchPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/SearchPage/SearchPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLibraryPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/LibraryPage/LibraryPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/LibraryPage/LibraryPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreatePlaylistPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/CreatePlaylistPage/CreatePlaylistPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/CreatePlaylistPage/CreatePlaylistPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLikedSongsPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/LikedSongsPage/LikedSongsPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/LikedSongsPage/LikedSongsPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToManagementPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/ManagementPage/ManagementPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/ManagementPage/ManagementPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPlaylistPage(MouseEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/PlaylistPage/PlaylistPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/PlaylistPage/PlaylistPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogInPageFromMenuBar(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/LogInSignUpPage/LogInPage.fxml"));
        stage = (Stage)((MenuItem)e.getTarget()).getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/LogInSignUpPage/LogInSignUp.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogInPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/LogInSignUpPage/LogInPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/LogInSignUpPage/LogInSignUp.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSignOutPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/LogInSignUpPage/SignUpPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(Main.class.getResource("Pages/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/LogInSignUpPage/LogInSignUp.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

//    Create new playlist
    public HBox createListPlaylist(VBox wrapper, String topic) {
        VBox container = new VBox();
        Label title = new Label();
        HBox playlistItems = new HBox();

        container.setPrefHeight(368);
        container.setPrefWidth(1290);
        container.setSpacing(8);

        title.getStyleClass().add("topic-playlist");
        title.setText(topic);

        playlistItems.setPrefHeight(310);
        playlistItems.setPrefWidth(1234);
        playlistItems.getStyleClass().add("playlist-tray");
        playlistItems.setSpacing(20);

        container.getChildren().addAll(title, playlistItems);

        wrapper.getChildren().add(container);

        return playlistItems;
    }

    public VBox createPlaylist(String nameOfPlaylist, String nameOfArtist) {
        VBox playlist = new VBox();
        StackPane imageWrapper = new StackPane();
        ImageView playlistImage = new ImageView();
        Label playlistName = new Label();
        Label artistsName = new Label();

        playlistImage.setFitWidth(188);
        playlistImage.setFitHeight(190);
        playlistImage.setImage(new Image(Main.class.getResourceAsStream("395eb4a5e6aa04c8574425345cd816f5.894x894x1.png")));

        imageWrapper.getChildren().add(playlistImage);
        imageWrapper.getStyleClass().add("img-playlist");

        playlistName.setText(nameOfPlaylist);
        playlistName.getStyleClass().add("playlist-name");

        artistsName.setText(nameOfArtist);
        artistsName.getStyleClass().add("artist-playlist");

        playlist.getChildren().addAll(imageWrapper, playlistName, artistsName);
        playlist.getStyleClass().add("playlist");
        playlist.setSpacing(14);

        return playlist;
    }

    public void setEventForPlaylist(ArrayList<VBox> playlists) {
        for (VBox playlist : playlists) {
            playlist.setOnMouseClicked((e) -> {
                try {
                    switchToPlaylistPage(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }


//    Center an image in ImageView
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);
        }
    }
}
