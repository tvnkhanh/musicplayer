package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;
import ptit.tvnkhanh.musicplayerproject.view.TrackBar;

import java.io.File;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchPageController extends Controller implements Initializable  {
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
    private TextField searchField;
    @FXML
    private VBox searchResultPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        searchField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                searchResultPane.getChildren().clear();

                try {
                    Connection con = DatabaseHelper.openConnection();
                    CallableStatement stmt = con.prepareCall("{call SP_SEARCH(?)}");
                    stmt.setNString(1, searchField.getText());
                    Boolean hasResult = stmt.execute();
                    if (hasResult) {
                        ResultSet rs = stmt.getResultSet();
                        int index = 1;

                        while (rs.next()) {
                            File fileSong = new File(rs.getString("FILE_URI"));
                            Media media = new Media(fileSong.toURI().toString());
                            MediaPlayer mediaPlayer = new MediaPlayer(media);
                            String duration = playerBar.formatTime((int)Math.round(mediaPlayer.getTotalDuration().toSeconds()));
                            TrackBar trackBar = new TrackBar();
                            HBox song = trackBar.createSong(index, rs.getInt("TRACK_ID"), rs.getNString("NAME"),
                                    rs.getNString("ARTIST"), rs.getString("IMG"), rs.getNString("ALBUM"),
                                    rs.getNString("COUNTRY"));

                            handleEventClickSong(song, index, rs.getString("FILE_URI"), rs.getNString("NAME"),
                                    rs.getNString("ARTIST"), rs.getString("IMG"));

                            searchResultPane.getChildren().add(song);

                            index++;
                        }
                    }
                    stmt.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HBox renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }

    public void handleEventClickSong(HBox song, int index, String fileURI, String nameOfSong, String nameOfArtist, String imgURL) {
        song.setOnMouseClicked((e) -> {
            PlaylistPageController.currentPlaylist = "";
            PlayerBar.songs.clear();
            PlayerBar.songs.put(index - 1, new File(fileURI));
            if (PlayerBar.isRunning) {
                PlayerBar.mediaPlayer.stop();
            }
            PlayerBar.songNumber = index - 1;
            PlayerBar.songNumberList.clear();
            PlayerBar.songNumberList.add(index - 1);
            PlaylistPageController.playlistSongInfo.clear();
            playerBar.startPlaylist();
            playerBar.getPlayPauseBtn().setGraphic(playerBar.getPauseImg());
            playerBar.getSongName().setText(nameOfSong);
            playerBar.getArtistName().setText(nameOfArtist);
            playerBar.getSongImg().setImage(new Image(imgURL));
            playerBar.setNameOfSong(nameOfSong);
            playerBar.setNameOfArtist(nameOfArtist);
            playerBar.setImgURl(imgURL);

            PlayerBar.mediaPlayer.play();
            if (!PlayerBar.isRunning) {
                PlayerBar.isRunning = true;
            }
        });
    }
}
