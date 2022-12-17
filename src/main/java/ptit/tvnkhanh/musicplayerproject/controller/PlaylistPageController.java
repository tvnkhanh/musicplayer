package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.w3c.dom.events.MouseEvent;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.Track;
import ptit.tvnkhanh.musicplayerproject.Main;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;
import ptit.tvnkhanh.musicplayerproject.view.TrackBar;

import java.io.File;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    @FXML
    private VBox contentPlaylistContainer = new VBox();
    @FXML
    private AnchorPane wrapper = new AnchorPane();
    @FXML
    private ImageView imgPlaylist, userAvatar;
    @FXML
    private Label playlistTitle, playlistArtist;
    @FXML
    private MenuButton userManagement;
    @FXML
    private Button managementBtn, playPlaylistBtn;
    public static String currentPlaylist;
    private ArrayList<File> playlistSongs = new ArrayList<>();
    private ImageView playImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-play-button-circled-50.png")));
    private ImageView pauseImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-pause-button-50.png")));

    private HBox renderPlayerBar;

    public Button getPlayPlaylistBtn() {
        return playPlaylistBtn;
    }

    public ImageView getPlayImg() {
        return playImg;
    }

    public ImageView getPauseImg() {
        return pauseImg;
    }

    public PlaylistPageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        imgPlaylist.setImage(new Image(onClickPlaylist.getImageURI()));
        playlistTitle.setText(onClickPlaylist.getTitle());
        playlistArtist.setText(onClickPlaylistArtist);

        try {
            Connection con = DatabaseHelper.openConnection();
            CallableStatement stmt = con.prepareCall("{call SP_PLAYLIST_TRACKS(?)}");
            stmt.setInt(1, onClickPlaylistId);
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();
                int index = 1;

                while (rs.next()) {
                    File fileSong = new File(rs.getString("FILE_URI"));
                    playlistSongs.add(fileSong);
                    Media media = new Media(fileSong.toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    String duration = playerBar.formatTime((int)Math.round(mediaPlayer.getTotalDuration().toSeconds()));
                    TrackBar trackBar = new TrackBar();
                    HBox song = trackBar.createSong(index, rs.getNString("NAME"), rs.getNString("ARTIST"), rs.getString("IMG"),
                            rs.getNString("ALBUM"), duration);

                    setActionForTrack(song, index, rs.getNString("NAME"), rs.getNString("ARTIST"), rs.getString("IMG"));

                    contentPlaylistContainer.getChildren().add(song);

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

        playPlaylistBtn.setOnAction((e) -> {
            if (PlayerBar.songs != playlistSongs) {
                PlayerBar.songs = playlistSongs;
                playerBar.startPlaylist(0);
                currentPlaylist = onClickPlaylist.getTitle();
            }
            if (PlayerBar.isRunning == true) {
                playPlaylistBtn.setGraphic(playImg);
                playerBar.playPauseSong();
            } else {
                playPlaylistBtn.setGraphic(pauseImg);
                playerBar.playPauseSong();
            }
        });

        if (Objects.equals(onClickPlaylist.getTitle(), currentPlaylist) && PlayerBar.isRunning == true) {
            playPlaylistBtn.setGraphic(pauseImg);
        } else {
            playPlaylistBtn.setGraphic(playImg);
        }

        renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }

    public void setActionForTrack(HBox song, int index, String nameOfSong, String nameOfArtist, String imgURL) {
        song.setOnMouseClicked((e) -> {
            if (PlayerBar.isRunning == true) {
                PlayerBar.mediaPlayer.stop();
            }
            PlayerBar.songs = playlistSongs;
            playerBar.startPlaylist(index - 1);
            if (PlayerBar.isRunning == true) {
                playerBar.getPlayPauseBtn().setGraphic(playerBar.getPauseImg());
                playerBar.getSongName().setText(nameOfSong);
                playerBar.getArtistName().setText(nameOfArtist);
                playerBar.getSongImg().setImage(new Image(imgURL));
                playerBar.setNameOfSong(nameOfSong);
                playerBar.setNameOfArtist(nameOfArtist);
                playerBar.setImgURl(imgURL);
                playPlaylistBtn.setGraphic(pauseImg);
                PlayerBar.mediaPlayer.play();

                currentPlaylist = onClickPlaylist.getTitle();
            } else {
                playerBar.getPlayPauseBtn().setGraphic(playerBar.getPauseImg());
                playerBar.getSongName().setText(nameOfSong);
                playerBar.getArtistName().setText(nameOfArtist);
                playerBar.getSongImg().setImage(new Image(imgURL));
                playerBar.setNameOfSong(nameOfSong);
                playerBar.setNameOfArtist(nameOfArtist);
                playerBar.setImgURl(imgURL);
                playPlaylistBtn.setGraphic(pauseImg);
                PlayerBar.mediaPlayer.play();
                PlayerBar.isRunning = true;

                currentPlaylist = onClickPlaylist.getTitle();
            }
        });
    }
}
