package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.model.Playlist;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatePlaylistPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private Playlist playlist;
    @FXML
    private AnchorPane wrapper;
    @FXML
    private MenuButton userManagement;
    @FXML
    private Button managementBtn;
    @FXML
    private ImageView userAvatar;
    @FXML
    private Label playlistTitle = new Label();
    @FXML
    private Label playlistArtist = new Label();
    @FXML
    private ImageView imgPlaylist = new ImageView();
    private PlaylistInfoPageController playlistInfo = new PlaylistInfoPageController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        playlistTitle.setText(playlistInfo.getPlaylistName());
        playlistArtist.setText(playlistInfo.getPlaylistDesc());
        imgPlaylist.setImage(new Image(playlistInfo.getPlaylistImgLink()));

        HBox renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }
}
