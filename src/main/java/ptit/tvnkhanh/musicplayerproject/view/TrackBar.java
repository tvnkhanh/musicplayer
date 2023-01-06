package ptit.tvnkhanh.musicplayerproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ptit.tvnkhanh.database.DAO.FavoriteSongsDAO;
import ptit.tvnkhanh.database.model.FavoriteSongs;
import ptit.tvnkhanh.musicplayerproject.Main;
import ptit.tvnkhanh.musicplayerproject.controller.LogInController;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrackBar {
    private HBox wrapper = new HBox();
    private Label trackId = new Label();
    private HBox trackInfoContainer = new HBox();
    private StackPane imageContainer = new StackPane();
    private ImageView trackImg = new ImageView();
    private VBox trackInfo = new VBox();
    private Label trackName = new Label();
    private Label artistName = new Label();
    private Label albumName = new Label();
    private Button favoriteBtn = new Button();
    private ImageView favoriteImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-favorite-28.png")));
    private ImageView unFavoriteImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-not-favorite-28.png")));
    private Label duration = new Label();

    public TrackBar() {
    }

    //    Create a song item in list of songs
    public HBox createSong(int id, int trackIdNum, String songN, String artistN, String imgUrl, String albumN, String time) throws SQLException {
        AtomicBoolean isFavorite = new AtomicBoolean(false);

        FavoriteSongsDAO favoriteSongsDAO = new FavoriteSongsDAO();
        FavoriteSongs favoriteSongs = new FavoriteSongs(LogInController.currentUser.getUserId(), trackIdNum);

        List<String> likedSongs = favoriteSongsDAO.getFavoriteSongs(LogInController.currentUser.getUserId());
        for (String likedSong : likedSongs) {
            if (Objects.equals(likedSong, songN)) {
                isFavorite.set(true);
            }
        }

        wrapper.setMaxWidth(1090);
        wrapper.setMaxHeight(60);
        wrapper.setMinWidth(1090);
        wrapper.setMinHeight(60);
        wrapper.setPadding(new Insets(0, 0, 0, 24));
        wrapper.getStyleClass().add("song-item");

        trackId.setPrefWidth(35);
        trackId.setPrefHeight(60);
        trackId.getStyleClass().add("song-info");
        trackId.setText((String.valueOf(id)));

        trackInfoContainer.setPrefWidth(630);
        trackInfoContainer.setPrefHeight(60);

        imageContainer.setPrefWidth(60);
        imageContainer.setPrefHeight(60);
        imageContainer.setPadding(new Insets(8));
        HBox.setMargin(imageContainer, new Insets(0, 14, 0, 0));
        trackImg.setFitWidth(44);
        trackImg.setFitHeight(44);
        trackImg.setImage(new Image(imgUrl));
        imageContainer.getChildren().add(trackImg);

        trackInfo.setPrefHeight(60);

        trackName.setPrefHeight(30);
        trackName.setText(songN);
        trackName.getStyleClass().add("song-name");
        trackName.setPadding(new Insets(10,0,0,0));
        artistName.setPrefHeight(30);
        artistName.setText(artistN);
        artistName.getStyleClass().add("artist-name");
        artistName.setPadding(new Insets(0,0,10,0));
        trackInfo.getChildren().addAll(trackName, artistName);

        trackInfoContainer.getChildren().addAll(imageContainer, trackInfo);

        albumName.setPrefWidth(300);
        albumName.setPrefHeight(60);
        albumName.setText(albumN);
        albumName.getStyleClass().add("song-info");

        favoriteBtn.setPrefWidth(28);
        favoriteBtn.setPrefHeight(28);
        if (isFavorite.get()) {
            favoriteBtn.setGraphic(favoriteImg);
        } else {
            favoriteBtn.setGraphic(unFavoriteImg);
        }
        favoriteBtn.getStyleClass().add("favorite-btn");

        duration.setPrefWidth(100);
        duration.setPrefHeight(60);
        duration.setText(time);
        duration.getStyleClass().add("song-info");

        wrapper.getChildren().addAll(trackId, trackInfoContainer,albumName, favoriteBtn, duration);
        wrapper.setAlignment(Pos.CENTER_LEFT);

        favoriteBtn.setOnAction(e -> {
            if (isFavorite.get()) {
                favoriteBtn.setGraphic(unFavoriteImg);
                isFavorite.set(false);
                try {
                    favoriteSongsDAO.deleteFavoriteSongs(favoriteSongs);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                favoriteBtn.setGraphic(favoriteImg);
                isFavorite.set(true);
                try {
                    favoriteSongsDAO.insertFavoriteSongs(favoriteSongs);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        return wrapper;
    }
}
