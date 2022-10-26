package ptit.tvnkhanh.musicplayerproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LikedSongsPageController extends Controller implements Initializable {
    @FXML
    private AnchorPane wrapper;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HBox playerBar = createMusicPlayerBar("Nevada", "tukhanh");
        wrapper.getChildren().add(playerBar);
    }
}
