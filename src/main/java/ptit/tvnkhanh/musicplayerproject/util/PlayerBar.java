package ptit.tvnkhanh.musicplayerproject.util;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.musicplayerproject.Main;
import ptit.tvnkhanh.musicplayerproject.controller.Controller;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerBar {
    private Controller controller = new Controller();
    private double prefWidthSongInfoCorner = 460;
    private double prefWidthPlayerControllerContainer = controller.getPrefWidthApp() - prefWidthSongInfoCorner * 2;
    private double prefWidthSongVolume = prefWidthSongInfoCorner;


    // Variable handle logic play, pause, shuffle, loop and volume event
    public static boolean isRunning = false;
    public static boolean isLoop = false;
    public static boolean isShuffle = false;
    public static boolean isMute = false;
    public static int songNumber;
    public static ArrayList<File> songs;
    public static ArrayList<File> backupSongs;
    public static Media media;
    public static MediaPlayer mediaPlayer;

    public static File directory;
    public static File[] files;
    public static Timeline timeLine;
    public static  double volumeValue = 1;

    // Create variable for UI
    private HBox wrapper = new HBox();
    // ========================================
    private HBox songInfoContainer = new HBox();

    private StackPane songImgContainer = new StackPane();
    private ImageView songImg = new ImageView(new Image(Main.class.getResourceAsStream("395eb4a5e6aa04c8574425345cd816f5.894x894x1.png")));

    private VBox songInfo = new VBox();
    private Label songName = new Label();
    private Label artistName = new Label();
    // ==========================================
    private VBox playerControllerContainer = new VBox();

    private HBox playerController = new HBox();
    private Button shuffleBtn = new Button();
    private Button prevBtn = new Button();
    private Button playPauseBtn = new Button();
    private Button nextBtn = new Button();
    private Button loopBtn = new Button();
    private ImageView shuffleImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-shuffle-28.png")));
    private ImageView activeShuffleImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-active-shuffle-28.png")));
    private ImageView prevImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-skip-to-start-28.png")));
    private ImageView playImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-circled-play-36.png")));
    private ImageView pauseImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-pause-button-36.png")));
    private ImageView nextImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-end-28.png")));
    private ImageView loopImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-update-left-rotation-28.png")));
    private ImageView activeLoopImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-active-update-left-rotation-28.png")));

    private HBox songProgressContainer = new HBox();
    private Label timeStart = new Label();
    private Label timeEnd = new Label();
    private StackPane slidoProgressBar = new StackPane();
    private Slider songSlider = new Slider();
    private ProgressBar songProgressBar = new ProgressBar();
    // ===================================
    private HBox volumeSettingContainer = new HBox();

    private Button volumeBtn = new Button();
    private ImageView volumeImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-voice-28.png")));
    private ImageView muteImg = new ImageView(new Image(Main.class.getResourceAsStream("Images/icons8-mute-28.png")));
    private Slider volumeSlider = new Slider();

    // ====================================
    private double btnSize = 28;
    private double imgBtnSize = 20;
    private double playPauseBtnSize = 36;
    private double playPauseImgSize = 32;
    private double progressBarHeightSize = 10;
    private double progressBarWidthSize = prefWidthPlayerControllerContainer - 40 * 2;

    public PlayerBar() {
    }

    //    Handle play, pause, shuffle, loop, change volume and progress music
    public HBox createMusicPlayerBar(String nameOfSong, String nameOfArtist) {
        // Render UI
        wrapper.setPrefWidth(controller.getPrefWidthApp());
        wrapper.setPrefHeight(controller.getPrefHeightPlayer());
        wrapper.setLayoutX(0);
        wrapper.setLayoutY(controller.getPrefHeightApp() - controller.getPrefHeightPlayer());
        wrapper.getStyleClass().add("music-media-player");

        songInfoContainer.setPrefWidth(prefWidthSongInfoCorner);
        songInfoContainer.setPrefHeight(controller.getPrefHeightPlayer());
        songImgContainer.setPrefWidth(controller.getPrefHeightPlayer());
        songImgContainer.setPrefHeight(controller.getPrefHeightPlayer());
        songImgContainer.setPadding(new Insets(23));
        HBox.setMargin(songImgContainer, new Insets(0, 14, 0, 14));
        songImg.setFitWidth(56);
        songImg.setFitHeight(56);
        songImgContainer.getChildren().add(songImg);
        songImgContainer.setAlignment(Pos.BASELINE_LEFT);
        songName.setText(nameOfSong);
        songName.setPadding(new Insets(24, 0,0,0));
        artistName.setText(nameOfArtist);
        artistName.setPadding(new Insets(0,0,40,0));
        songName.getStyleClass().add("song-name-corner");
        artistName.getStyleClass().add("artist-name-corner");
        songInfo.getChildren().addAll(songName, artistName);
        songInfoContainer.getChildren().addAll(songImgContainer, songInfo);

        playerControllerContainer.setPrefWidth(prefWidthPlayerControllerContainer);
        playerControllerContainer.setPrefHeight(controller.getPrefHeightPlayer());
        playerController.setPrefHeight(controller.getPrefHeightPlayer() / 2);
        songProgressContainer.setPrefHeight(controller.getPrefHeightPlayer() / 2);
        shuffleBtn.setGraphic(shuffleImg);
        prevBtn.setGraphic(prevImg);
        playPauseBtn.setGraphic(playImg);
        nextBtn.setGraphic(nextImg);
        loopBtn.setGraphic(loopImg);
        shuffleBtn.setPrefWidth(btnSize);
        shuffleBtn.setPrefHeight(btnSize);
        shuffleImg.setFitWidth(imgBtnSize);
        shuffleImg.setFitHeight(imgBtnSize);
        activeShuffleImg.setFitWidth(imgBtnSize);
        activeShuffleImg.setFitHeight(imgBtnSize);
        prevBtn.setPrefWidth(btnSize);
        prevBtn.setPrefHeight(btnSize);
        prevImg.setFitWidth(imgBtnSize);
        prevImg.setFitHeight(imgBtnSize);
        playPauseBtn.setPrefWidth(playPauseBtnSize);
        playPauseBtn.setPrefHeight(playPauseBtnSize);
        playImg.setFitWidth(playPauseImgSize);
        playImg.setFitHeight(playPauseImgSize);
        pauseImg.setFitWidth(playPauseImgSize);
        pauseImg.setFitHeight(playPauseImgSize);
        nextBtn.setPrefWidth(btnSize);
        nextBtn.setPrefHeight(btnSize);
        nextImg.setFitWidth(imgBtnSize);
        nextImg.setFitHeight(imgBtnSize);
        loopBtn.setPrefWidth(btnSize);
        loopBtn.setPrefHeight(btnSize);
        loopImg.setFitWidth(imgBtnSize);
        loopImg.setFitHeight(imgBtnSize);
        activeLoopImg.setFitWidth(imgBtnSize);
        activeLoopImg.setFitHeight(imgBtnSize);
        shuffleBtn.getStyleClass().add("media-player-btn");
        prevBtn.getStyleClass().add("media-player-btn");
        playPauseBtn.getStyleClass().add("media-player-btn");
        nextBtn.getStyleClass().add("media-player-btn");
        loopBtn.getStyleClass().add("media-player-btn");
        volumeBtn.getStyleClass().add("media-player-btn");
        if (isRunning == true) {
            playPauseBtn.setGraphic(pauseImg);
            playerController.getChildren().addAll(shuffleBtn, prevBtn, playPauseBtn, nextBtn, loopBtn);
        } else {
            playPauseBtn.setGraphic(playImg);
            playerController.getChildren().addAll(shuffleBtn, prevBtn, playPauseBtn, nextBtn, loopBtn);
        }
        if (isLoop == true) {
            loopBtn.setGraphic(activeLoopImg);
        } else {
            loopBtn.setGraphic(loopImg);
        }
        if (isShuffle == true) {
            shuffleBtn.setGraphic(activeShuffleImg);
        } else {
            shuffleBtn.setGraphic(shuffleImg);
        }
        playerController.setAlignment(Pos.CENTER);
        playerController.setPadding(new Insets(20, 150, 0, 150));
        playerController.setSpacing(24);
        timeStart.setPrefWidth(30);
        timeStart.getStyleClass().add("duration");
        timeEnd.setPrefWidth(30);
        timeEnd.getStyleClass().add("duration");
        songSlider.setPrefWidth(progressBarWidthSize);
        songSlider.setPrefHeight(progressBarHeightSize);
        songSlider.setMin(0);
        songSlider.setMax(1);
        songSlider.getStyleClass().add("slider-progress-bar");
        songProgressBar.setPrefWidth(progressBarWidthSize);
        songProgressBar.setPrefHeight(progressBarHeightSize);
        slidoProgressBar.getChildren().addAll(songProgressBar, songSlider);
        slidoProgressBar.setPadding(new Insets(0, 0, 34, 0));
        songProgressContainer.getChildren().addAll(timeStart, slidoProgressBar, timeEnd);
        songProgressContainer.setSpacing(8);
        playerControllerContainer.getChildren().addAll(playerController, songProgressContainer);

        volumeSettingContainer.setPrefWidth(prefWidthSongVolume);
        volumeSettingContainer.setPrefHeight(controller.getPrefHeightPlayer());
        volumeBtn.setGraphic(volumeImg);
        volumeBtn.setPrefWidth(btnSize);
        volumeBtn.setPrefHeight(btnSize);
        volumeImg.setFitWidth(imgBtnSize);
        volumeImg.setFitHeight(imgBtnSize);
        muteImg.setFitWidth(imgBtnSize);
        muteImg.setFitHeight(imgBtnSize);
        volumeSlider.setPrefWidth(130);
        volumeSlider.setValue(volumeValue);
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSettingContainer.getChildren().addAll(volumeBtn, volumeSlider);
        volumeSettingContainer.setAlignment(Pos.BASELINE_CENTER);
        volumeSettingContainer.setSpacing(6);
        volumeSettingContainer.setPadding(new Insets(40,0,0,250));

        wrapper.getChildren().addAll(songInfoContainer, playerControllerContainer, volumeSettingContainer);

        // Handle logic
        playPauseBtn.setOnAction(actionEvent -> playPauseSong());
        nextBtn.setOnAction(actionEvent -> nextSong());
        prevBtn.setOnAction(actionEvent -> prevSong());
        trackProgress();

        songSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                timeLine.stop();
                timeLine.getKeyFrames().clear();
                timeLine = null;
                double current = (mouseEvent.getX() / songSlider.getWidth()) * songSlider.getMax();
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(current));
                if (isRunning == false) {
                    playPauseSong();
                } else {
                    trackProgress();
                }
            }
        });

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                volumeValue = volumeSlider.getValue();
                if (volumeValue > 0) {
                    isMute = false;
                    volumeBtn.setGraphic(volumeImg);
                } else {
                    isMute = true;
                    volumeBtn.setGraphic(muteImg);
                }
                mediaPlayer.setVolume(volumeValue);
            }
        });

        volumeBtn.setOnAction(actionEvent -> {
            if (isMute == false) {
                mediaPlayer.setVolume(0);
                volumeSlider.setValue(0);
                volumeBtn.setGraphic(muteImg);
                isMute = true;
            }
        });

        loopBtn.setOnAction(actionEvent -> {
            if (isLoop == false) {
                isLoop = true;
                loopSong();
                loopBtn.setGraphic(activeLoopImg);
            } else {
                isLoop = false;
                loopSong();
                loopBtn.setGraphic(loopImg);
            }
        });

        shuffleBtn.setOnAction(actionEvent -> {
            if (isShuffle == false) {
                isShuffle = true;
                shuffleBtn.setGraphic(activeShuffleImg);
                shuffleQueue();
            } else {
                isShuffle = false;
                shuffleBtn.setGraphic(shuffleImg);
                shuffleQueue();
            }
        });

        return wrapper;
    }


    public void playPauseSong() {
        if (isRunning == false) {
            mediaPlayer.play();
            mediaPlayer.setVolume(volumeValue);
            playPauseBtn.setGraphic(pauseImg);
            trackProgress();
            isRunning = true;
        } else {
            mediaPlayer.pause();
            playPauseBtn.setGraphic(playImg);
            timeLine.pause();
            isRunning = false;
        }
    }

    public void nextSong() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
        }
        else {
            songNumber = 0;
        }
        mediaPlayer.stop();
        isRunning = false;
        if (timeLine != null) {
            timeLine.stop();
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        playPauseSong();
    }

    public void prevSong() {
        if (songNumber > 0) {
            songNumber--;
        }
        else {
            songNumber = songs.size() - 1;
        }
        mediaPlayer.stop();
        isRunning = false;
        if (timeLine != null) {
            timeLine.stop();
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        playPauseSong();
    }

    public void autoNextSong() {
        double current = mediaPlayer.getCurrentTime().toSeconds();
        double end = media.getDuration().toSeconds();
        if (current == end && isLoop == false) {
            nextSong();
            trackProgress();
        }
    }

    public void loopSong() {
        if (isLoop == true) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    isRunning = false;
                    mediaPlayer.seek(Duration.ZERO);
                    playPauseSong();
                }
            });
        } else {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    autoNextSong();
                }
            });
        }
    }

    public void shuffleQueue() {
        if (isShuffle == true) {
            Collections.shuffle(songs);
        } else {
            songs = backupSongs;
        }
    }

    public String formatTime(int n) {
        int minute, second;
        minute = n / 60;
        second = n % 60;
        if (second < 10) {
            return minute + ":0" + second;
        }
        return minute + ":" + second;
    }

    public void trackProgress() {
        timeLine = new Timeline(new KeyFrame(Duration.seconds(0.01), e -> {
            double current = mediaPlayer.getCurrentTime().toSeconds();
            double end = media.getDuration().toSeconds();

            String currentFormat = formatTime((int)Math.round(current));
            String endFormat = formatTime((int)Math.round(end));

            timeStart.setText(currentFormat);
            timeEnd.setText(endFormat);

            songProgressBar.setProgress(current / end);
            songSlider.setValue(current / end);

            autoNextSong();
        }));
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
    }

    public void startPlaylist() throws SQLException {
        songs = new ArrayList<>();

        Connection con = DatabaseHelper.openConnection();
        Statement statement = con.createStatement();
        String sql = "SELECT FILE_URI FROM TRACK";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String filePath = rs.getNString("FILE_URI");
            File fileSong = new File(filePath);
            songs.add(fileSong);
        }
        backupSongs = songs;
        songNumber = 0;
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        statement.close();
        con.close();
    }
}
