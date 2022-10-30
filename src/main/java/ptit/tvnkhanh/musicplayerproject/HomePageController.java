package ptit.tvnkhanh.musicplayerproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController extends Controller implements Initializable {
    @FXML
    private AnchorPane wrapper;
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
        VBox playlist5 = createPlaylist("Taylor", "Tu Khanh");
        VBox playlist6 = createPlaylist("Taylor", "Tu Khanh");
        VBox playlist7 = createPlaylist("Taylor", "Tu Khanh");
        playlistTray2.getChildren().addAll(playlist3, playlist4, playlist5, playlist6, playlist7);

        HBox playlistTray3 = createListPlaylist(contentHomePage, "Something");
        VBox playlist8 = createPlaylist("Taylor", "Tu Khanh");
        VBox playlist9 = createPlaylist("Taylor", "Tu Khanh");
        playlistTray3.getChildren().addAll(playlist8, playlist9);



        playlists.add(playlist1);
        playlists.add(playlist2);
        playlists.add(playlist3);
        playlists.add(playlist4);
        playlists.add(playlist5);
        playlists.add(playlist6);
        playlists.add(playlist7);
        playlists.add(playlist8);
        playlists.add(playlist9);
        setEventForPlaylist(playlists);

        if (Controller.mediaPlayer == null) {
            startPlaylist();
        }
        HBox playerBar = createMusicPlayerBar("Nevada", "tukhanh");
        wrapper.getChildren().add(playerBar);
    }
}
