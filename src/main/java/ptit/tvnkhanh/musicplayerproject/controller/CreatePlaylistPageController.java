package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ptit.tvnkhanh.musicplayerproject.util.PlayerBar;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatePlaylistPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    @FXML
    private AnchorPane wrapper;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HBox renderPlayerBar = playerBar.createMusicPlayerBar("Nevada", "tukhanh");
        wrapper.getChildren().add(renderPlayerBar);
    }
}
