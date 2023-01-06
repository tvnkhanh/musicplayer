package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.musicplayerproject.Main;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;
import ptit.tvnkhanh.musicplayerproject.view.TrackBar;

import java.io.File;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ptit.tvnkhanh.musicplayerproject.controller.PlaylistPageController.currentPlaying;

public class LikedSongsPageController extends Controller implements Initializable {
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
    private Label likedSongArtist;
    @FXML
    private VBox contentPlaylistContainer;
    @FXML
    private Button playPlaylistBtn;
    private ImageView playImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-play-button-circled-50.png")));
    private ImageView pauseImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-pause-button-50.png")));
    private HashMap<Integer, File> playlistSongs = new HashMap<Integer, File>();
    public static HashMap<Integer, List<String>> playlistSongInfoTemp = new HashMap<Integer, List<String>>();
    public static HashMap<Integer, List<String>> playlistSongInfo = new HashMap<Integer, List<String>>();
    private List<Integer> songNumberTemp = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        likedSongArtist.setText("Made by " + LogInController.currentUser.getUserName());

        try {
            Connection con = DatabaseHelper.openConnection();
            CallableStatement stmt = con.prepareCall("{call SP_ALL_LIKED_SONG(?)}");
            stmt.setInt(1, LogInController.currentUser.getUserId());
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();
                int index = 1;

                while (rs.next()) {
                    File fileSong = new File(rs.getString("FILE_URI"));
                    playlistSongs.put(index - 1, fileSong);
                    TrackBar trackBar = new TrackBar();
                    HBox song = trackBar.createSong(index, rs.getInt("TRACK_ID"), rs.getNString("NAME"),
                            rs.getNString("ARTIST"), rs.getString("IMG"), rs.getNString("ALBUM"),
                            rs.getNString("COUNTRY"));

                    String nameOfSong = rs.getNString("NAME");
                    String nameOfArtist = rs.getNString("ARTIST");
                    String imgURL = rs.getString("IMG");
                    String[] array = new String[] {nameOfSong, nameOfArtist, imgURL};

                    playlistSongInfoTemp.put(index, Arrays.asList(array));
                    songNumberTemp.add(index - 1);

                    setActionForLikedTrack(song, index, nameOfSong, nameOfArtist, imgURL);

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
                PlayerBar.songNumber = 0;
                playerBar.startPlaylist();
                playlistSongInfo = playlistSongInfoTemp;
            }
            if (PlayerBar.isRunning) {
                playPlaylistBtn.setGraphic(playImg);
                playerBar.playPauseSong();
            } else {
                playPlaylistBtn.setGraphic(pauseImg);
                playerBar.playPauseSong();
            }
        });

        currentPlaying.addListener((ListChangeListener<String>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    playerBar.getSongName().setText(currentPlaying.get(0));
                    playerBar.getArtistName().setText(currentPlaying.get(1));
                    playerBar.getSongImg().setImage(new Image(currentPlaying.get(2)));
                    playerBar.setNameOfSong(currentPlaying.get(0));
                    playerBar.setNameOfArtist(currentPlaying.get(1));
                    playerBar.setImgURl(currentPlaying.get(2));
                }
            }
        });

        HBox renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }

    public void setActionForLikedTrack(HBox song, int index, String nameOfSong, String nameOfArtist, String imgURL) {
        song.setOnMouseClicked((e) -> {
            if (PlayerBar.isRunning) {
                PlayerBar.mediaPlayer.stop();
            }
            PlayerBar.songNumberList = songNumberTemp;
            PlayerBar.songNumber = index - 1;
            PlayerBar.songs = playlistSongs;
            playerBar.startPlaylist();
            playlistSongInfo = playlistSongInfoTemp;
            currentPlaying.clear();
            currentPlaying.addAll(nameOfSong, nameOfArtist, imgURL);

            playerBar.getPlayPauseBtn().setGraphic(playerBar.getPauseImg());
            playPlaylistBtn.setGraphic(pauseImg);
            PlayerBar.mediaPlayer.play();

            if (!PlayerBar.isRunning) {
                PlayerBar.isRunning = true;
            }

        });
    }
}
