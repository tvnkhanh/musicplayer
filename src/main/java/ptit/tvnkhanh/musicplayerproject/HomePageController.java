package ptit.tvnkhanh.musicplayerproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController extends Controller implements Initializable {

    @FXML
    private Button LikedSongsBtn;

    @FXML
    private Button createPlayListBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ScrollPane homeContentPane;

    @FXML
    private Button libraryBtn;

    @FXML
    private Button loopBtn;

    @FXML
    private ProgressBar musicProgressBar;

    @FXML
    private Button nextBtn;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button playBtn;

    @FXML
    private Pane playerPane;

    @FXML
    private Button prevBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Button shuffleBtn;

    @FXML
    private Pane sideBar;

    @FXML
    private Button volumeBtn;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ImageView homePageBanner;
    @FXML
    private VBox contentHomePage;
    private ArrayList<VBox> playlists = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        centerImage(homePageBanner);


        contentHomePage.setSpacing(-100);

        HBox playlistTray = createListPlaylist(contentHomePage, "Something");
        VBox playlist1 = createPlaylist("Taylor", "Tu Khanh");
        VBox playlist2 = createPlaylist("Taylor", "Tu Khanh");
        playlistTray.getChildren().addAll(playlist1, playlist2);

        HBox playlistTray2 = createListPlaylist(contentHomePage, "Something else");
        VBox playlist3 = createPlaylist("Taylor", "Tu Khanh");
        VBox playlist4 = createPlaylist("Taylor", "Tu Khanh");
        playlistTray2.getChildren().addAll(playlist3, playlist4);

        playlists.add(playlist1);
        playlists.add(playlist2);
        playlists.add(playlist3);
        playlists.add(playlist4);
        setEventForPlaylist(playlists);
    }


}
