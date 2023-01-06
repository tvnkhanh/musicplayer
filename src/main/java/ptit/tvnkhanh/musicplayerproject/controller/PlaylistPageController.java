package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ptit.tvnkhanh.database.DAO.PlaylistDAO;
import ptit.tvnkhanh.database.DAO.PlaylistDetailsDAO;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.PlaylistDetails;
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
    @FXML
    private VBox searchContent;
    @FXML
    private TextField searchField;
    @FXML
    private VBox searchResultPane;
    public static String currentPlaylist;
    private HashMap<Integer, File> playlistSongs = new HashMap<Integer, File>();
    private ImageView playImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-play-button-circled-50.png")));
    private ImageView pauseImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-pause-button-50.png")));

    private HBox renderPlayerBar;
    private List<Integer> songNumberTemp = new ArrayList<>();
    public static ObservableList<String> currentPlaying = FXCollections.observableArrayList();
    public static HashMap<Integer, List<String>> playlistSongInfoTemp = new HashMap<Integer, List<String>>();
    public static HashMap<Integer, List<String>> playlistSongInfo = new HashMap<Integer, List<String>>();
    private ManagementPageController managementPageController = new ManagementPageController();
    private PlaylistDetailsDAO playlistDetailsDAO = new PlaylistDetailsDAO();
    private PlaylistDetails playlistDetails = new PlaylistDetails();

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
            CallableStatement stmt = con.prepareCall("{call SP_PLAYLIST_TRACKS(?,?)}");
            stmt.setInt(1, onClickPlaylistUserId);
            stmt.setInt(2, onClickPlaylistId);
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

                    setActionForTrack(song, index, nameOfSong, nameOfArtist, imgURL);

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
                currentPlaylist = onClickPlaylist.getTitle();
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

        searchContent.setVisible(onClickPlaylistUserId != 1);

        if (Objects.equals(onClickPlaylist.getTitle(), currentPlaylist) && PlayerBar.isRunning) {
            playPlaylistBtn.setGraphic(pauseImg);
        } else {
            playPlaylistBtn.setGraphic(playImg);
        }

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
                            TrackBar trackBar = new TrackBar();
                            HBox song = trackBar.createSong(index, rs.getInt("TRACK_ID"), rs.getNString("NAME"),
                                    rs.getNString("ARTIST"), rs.getString("IMG"), rs.getNString("ALBUM"),
                                    rs.getNString("COUNTRY"));

                            handleEventAddSong(song, onClickPlaylistId, rs.getInt("TRACK_ID"));

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


        renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }

    public void setActionForTrack(HBox song, int index, String nameOfSong, String nameOfArtist, String imgURL) {
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

            currentPlaylist = onClickPlaylist.getTitle();
        });
    }

    public void handleEventAddSong(HBox song, int playlistId, int trackId) {
        song.setOnMouseClicked((e) -> {
            Optional<ButtonType> result = managementPageController.confirmationAlert("add", "song");
            if (result.get() == ButtonType.OK) {
                playlistDetails = new PlaylistDetails(playlistId, trackId);
                try {
                    playlistDetailsDAO.insertPlaylistDetails(playlistDetails);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
