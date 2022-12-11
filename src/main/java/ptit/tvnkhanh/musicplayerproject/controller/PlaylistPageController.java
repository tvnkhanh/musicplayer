package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ptit.tvnkhanh.musicplayerproject.util.PlayerBar;
import ptit.tvnkhanh.musicplayerproject.util.TrackBar;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PlaylistPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    @FXML
    private VBox contentPlaylistContainer = new VBox();
    @FXML
    private AnchorPane wrapper = new AnchorPane();
    TrackBar trackBar1 = new TrackBar();
    TrackBar trackBar2 = new TrackBar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HBox song1 = null;
        HBox song2 = null;
        try {
            song1 = trackBar1.createSong(1, "Nevada", "Vicetion", "395eb4a5e6aa04c8574425345cd816f5.894x894x1.png","Cozy", "3:04");
            song2 = trackBar2.createSong(2, "Stars Sky", "Two step from hell", "1234381.jpg","", "4:04");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        contentPlaylistContainer.getChildren().addAll(song1, song2);

        HBox renderPlayerBar = playerBar.createMusicPlayerBar("Nevada", "tukhanh");
        wrapper.getChildren().add(renderPlayerBar);
    }
}
