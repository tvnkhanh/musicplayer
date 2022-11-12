package ptit.tvnkhanh.musicplayerproject.model;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ptit.tvnkhanh.musicplayerproject.Main;

public class Track {
    private HBox wrapper = new HBox();
    private Label trackId = new Label();
    private HBox trackInfoContainer = new HBox();
    private StackPane imageContainer = new StackPane();
    private ImageView trackImg = new ImageView();
    private VBox trackInfo = new VBox();
    private Label trackName = new Label();
    private Label artistName = new Label();
    private Label albumName = new Label();
    private Label duration = new Label();

    public Track() {
    }

    //    Create a song item in list of songs
    public HBox createSong(int id, String songN, String artistN, String imgUrl, String albumN, String time) {
        wrapper.setPrefWidth(200);
        wrapper.setPrefHeight(60);
        wrapper.setPadding(new Insets(0, 0, 0, 30));
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
        trackImg.setImage(new Image(Main.class.getResourceAsStream(imgUrl)));
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

        albumName.setPrefWidth(350);
        albumName.setPrefHeight(60);
        albumName.setText(albumN);
        albumName.getStyleClass().add("song-info");

        duration.setPrefWidth(70);
        duration.setPrefHeight(60);
        duration.setText(time);
        duration.getStyleClass().add("song-info");

        wrapper.getChildren().addAll(trackId, trackInfoContainer,albumName,duration);
        wrapper.setAlignment(Pos.CENTER_LEFT);

        return wrapper;
    }
}
