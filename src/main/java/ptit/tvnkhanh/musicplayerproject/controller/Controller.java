package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.model.Artist;
import ptit.tvnkhanh.database.model.Playlist;
import ptit.tvnkhanh.musicplayerproject.Main;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    public static Playlist onClickPlaylist = new Playlist();
    public static String onClickPlaylistArtist;
    public static int onClickPlaylistId;
    public static int onClickPlaylistUserId;

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

    public void switchToHomePageKeyEvent(KeyEvent e) throws IOException {
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
        scene.getStylesheets().add(Main.class.getResource("Pages/PlaylistPage/PlaylistPage.css").toExternalForm());
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
        scene.getStylesheets().add(Main.class.getResource("Pages/PlaylistPage/PlaylistPage.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("Pages/CreatePlaylistPage/CreatePlaylistPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPlaylistInfoPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(Main.class.getResource("Pages/CreatePlaylistPage/PlaylistInfoPage.fxml"));
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
        if (PlayerBar.mediaPlayer != null) {
            PlayerBar.mediaPlayer.pause();
            PlayerBar.mediaPlayer.seek(new Duration(0));
            PlayerBar.isRunning = false;
            PlayerBar.songs = new HashMap<>();
            PlayerBar playerBar = new PlayerBar();
            playerBar.setImgURl(null);
            playerBar.setNameOfSong("");
            playerBar.setNameOfArtist("");
        }

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

    public void switchToLogInPage(KeyEvent e) throws IOException {
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

    public HBox createPlaylist(VBox wrapper, String topic, String currentPage) throws Exception {
        HBox playlistTray = createListPlaylist(wrapper, topic);
        PlaylistDAO playlistDAO = new PlaylistDAO();
        int index = 0;

        List<Playlist> playlists;
        List<Integer> playlistIds;
        if (currentPage == "Home") {
            playlists = playlistDAO.getAllPlaylist(1);
             playlistIds= playlistDAO.getPlaylistId(1);
        } else {
            playlists = playlistDAO.getAllPlaylist(LogInController.currentUser.getUserId());
            playlistIds= playlistDAO.getPlaylistId(LogInController.currentUser.getUserId());
        }

        for (Playlist playlist : playlists) {
            List<Artist> lstArtist;
            if (Objects.equals(currentPage, "Home")) {
                lstArtist = playlistDAO.getArtistInfo(1, playlistIds.get(index));
            } else {
                lstArtist = playlistDAO.getArtistInfo(LogInController.currentUser.getUserId(), playlistIds.get(index));
            }

            String nameOfPlaylist = playlist.getTitle();
            StringBuilder nameOfArtist = new StringBuilder();
            for (int i = 0; i < lstArtist.size(); i++) {
                if (i != lstArtist.size() - 1) {
                    nameOfArtist.append(lstArtist.get(i).getName()).append(", ");
                } else {
                    nameOfArtist.append(lstArtist.get(i).getName());
                }
            }

            VBox playlistUI = new VBox();
            StackPane imageWrapper = new StackPane();
            ImageView playlistImage = new ImageView();
            Label playlistName = new Label();
            Label artistsName = new Label();

            playlistImage.setFitWidth(188);
            playlistImage.setFitHeight(190);
            playlistImage.setImage(new Image(playlist.getImageURI()));

            imageWrapper.getChildren().add(playlistImage);
            imageWrapper.getStyleClass().add("img-playlist");

            playlistName.setText(nameOfPlaylist);
            playlistName.getStyleClass().add("playlist-name");

            artistsName.setText(nameOfArtist.toString());
            artistsName.getStyleClass().add("artist-playlist");
            artistsName.setMinWidth(160);
            artistsName.setMaxWidth(160);

            playlistUI.getChildren().addAll(imageWrapper, playlistName, artistsName);
            playlistUI.getStyleClass().add("playlist");
            playlistUI.setSpacing(14);

            playlistTray.getChildren().add(playlistUI);

            if (Objects.equals(currentPage, "Home")) {
                setEventForPlaylist(playlistUI, playlist, nameOfArtist.toString(), playlistIds.get(index), 1);
            } else {
                setEventForPlaylist(playlistUI, playlist, nameOfArtist.toString(), playlistIds.get(index),
                        LogInController.currentUser.getUserId());
            }
            index++;
        }
        return playlistTray;
    }

    public void setEventForPlaylist(VBox playlistUI, Playlist playlist, String artistNames, int playlistId, int userId) {
        playlistUI.setOnMouseClicked((e) -> {
            try {
                onClickPlaylist = playlist;
                onClickPlaylistArtist = artistNames;
                onClickPlaylistId = playlistId;
                onClickPlaylistUserId = userId;
                switchToPlaylistPage(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // Check role of user
    public void checkUser(MenuButton menuButton, Button managementBtn, ImageView userAvatar) {
        menuButton.setMinWidth(134);
        menuButton.setMaxWidth(134);
        if (Objects.equals(LogInController.currentUser.getRole(), "admin")) {
            menuButton.setText(LogInController.currentUser.getUserName());
            userAvatar.setImage(new Image(LogInController.currentUser.getAvatar_link()));
            managementBtn.setVisible(true);
        } else {
            menuButton.setText(LogInController.currentUser.getUserName());
            userAvatar.setImage(new Image(LogInController.currentUser.getAvatar_link()));
            managementBtn.setVisible(false);
        }
    }

    // Center an image in ImageView
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
