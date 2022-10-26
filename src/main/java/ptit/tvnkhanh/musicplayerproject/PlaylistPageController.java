package ptit.tvnkhanh.musicplayerproject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PlaylistPageController extends Controller implements Initializable {
    @FXML
    private VBox contentPlaylistContainer = new VBox();
    @FXML
    private AnchorPane wrapper = new AnchorPane();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HBox song1 = createSong(1, "Nevada", "Vicetion", "395eb4a5e6aa04c8574425345cd816f5.894x894x1.png","Cozy", "3:04");
        HBox song2 = createSong(2, "Stars Sky", "Two step from hell", "1234381.jpg","", "4:04");
        contentPlaylistContainer.getChildren().addAll(song1, song2);

        HBox playerBar = createMusicPlayerBar("Nevada", "tukhanh");
        wrapper.getChildren().add(playerBar);

    }
}
