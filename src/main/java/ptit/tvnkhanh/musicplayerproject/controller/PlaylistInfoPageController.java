package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.model.Playlist;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistInfoPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private Playlist playlist;
    @FXML
    private AnchorPane wrapper;
    @FXML
    private MenuButton userManagement;
    @FXML
    private Button managementBtn, selectImgBtn;
    @FXML
    private ImageView userAvatar, playlistImg;
    @FXML
    private TextField playlistCustomName;
    @FXML
    private TextArea playlistCustomDesc;

    private static String playlistName, playlistDesc, playlistImgLink;

    public String getPlaylistName() {
        return playlistName;
    }

    public String getPlaylistDesc() {
        return playlistDesc;
    }

    public String getPlaylistImgLink() {
        return playlistImgLink;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        HBox renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }
    @FXML
    public void selectImgFilePlaylist(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("img file", "*.png", "*jpg"));
        File f = fc.showOpenDialog(null);

        if (f != null) {
            playlistImg.setImage(new Image(f.toURI().toString()));
            playlistImgLink = f.toPath().toString();
        }
    }
    @FXML
    public void onClickSavePlaylist(ActionEvent e) throws Exception {
        playlistName = playlistCustomName.getText();
        playlistDesc = playlistCustomDesc.getText();

        playlist = new Playlist(playlistName, playlistImgLink, LogInController.currentUser.getUserId());
        playlistDAO.insertPlaylist(playlist);

        switchToCreatePlaylistPage(e);
    }
}
