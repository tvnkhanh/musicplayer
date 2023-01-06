package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;

import java.net.URL;
import java.util.ResourceBundle;

public class LibraryPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    @FXML
    private AnchorPane wrapper;
    @FXML
    private MenuButton userManagement;
    @FXML
    private Button managementBtn;
    @FXML
    private ImageView userAvatar;
    @FXML
    private VBox contentLibPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        try {
            createPlaylist(contentLibPage, "", "Library");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        HBox renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }
}
