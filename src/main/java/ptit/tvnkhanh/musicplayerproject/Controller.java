package ptit.tvnkhanh.musicplayerproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private double prefWidthApp = 1510;
    private double prefHeightApp = 750;
    private double prefHeightPlayer = 102;

    private double prefWidthSongInfoCorner = 460;
    private double prefWidthPlayerControllerContainer = prefWidthApp - prefWidthSongInfoCorner * 2;
    private double prefWidthSongVolume = prefWidthSongInfoCorner;


    // Variable handle logic play, pause, shuffle, loop and volume event
    public static boolean isRunning = false;
    public static boolean isLoop = false;
    public static boolean isShuffle = false;
    public static boolean isMute = false;
    public static int songNumber;
    public static ArrayList<File> songs;
    public static Media media;
    public static MediaPlayer mediaPlayer;

    public static File directory;
    public static File[] files;
    public static Timeline timeLine;
    public static  double volumeValue = 1;
//    Switch Page Method:
    public void switchToHomePage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Layout/HomePage/HomePage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Layout/GlobalStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSearchPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Layout/SearchPage/SearchPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Layout/GlobalStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLibraryPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Layout/LibraryPage/LibraryPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Layout/GlobalStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreatePlaylistPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Layout/CreatePlaylistPage/CreatePlaylistPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Layout/GlobalStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLikedSongsPage(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Layout/LikedSongsPage/LikedSongsPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Layout/GlobalStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPlaylistPage(MouseEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Layout/PlaylistPage/PlaylistPage.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Layout/GlobalStyle.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("Layout/PlaylistPage/PlaylistPage.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

//    Create new playlist
    public HBox createListPlaylist(VBox wrapper, String topic) {
        VBox container = new VBox();
        Label title = new Label();
        HBox playlistItems = new HBox();

        container.setPrefHeight(368);
        container.setPrefWidth(1290);
        container.setSpacing(8);

        title.getStyleClass().add("topic-playlist");
        title.setText(topic);

        playlistItems.setPrefHeight(310);
        playlistItems.setPrefWidth(1234);
        playlistItems.getStyleClass().add("playlist-tray");
        playlistItems.setSpacing(20);

        container.getChildren().addAll(title, playlistItems);

        wrapper.getChildren().add(container);

        return playlistItems;
    }

    public VBox createPlaylist(String nameOfPlaylist, String nameOfArtist) {
        VBox playlist = new VBox();
        StackPane imageWrapper = new StackPane();
        ImageView playlistImage = new ImageView();
        Label playlistName = new Label();
        Label artistsName = new Label();

        playlistImage.setFitWidth(188);
        playlistImage.setFitHeight(190);
        playlistImage.setImage(new Image(this.getClass().getResourceAsStream("395eb4a5e6aa04c8574425345cd816f5.894x894x1.png")));

        imageWrapper.getChildren().add(playlistImage);
        imageWrapper.getStyleClass().add("img-playlist");

        playlistName.setText(nameOfPlaylist);
        playlistName.getStyleClass().add("playlist-name");

        artistsName.setText(nameOfArtist);
        artistsName.getStyleClass().add("artist-playlist");

        playlist.getChildren().addAll(imageWrapper, playlistName, artistsName);
        playlist.getStyleClass().add("playlist");
        playlist.setSpacing(14);

        return playlist;
    }

    public void setEventForPlaylist(ArrayList<VBox> playlists) {
        for (VBox playlist : playlists) {
            playlist.setOnMouseClicked((e) -> {
                try {
                    switchToPlaylistPage(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }

//    Create a song item in list of songs
    public HBox createSong(int id,String songN, String artistN, String imgUrl, String albumN, String time) {
        HBox wrapper = new HBox();
        Label songId = new Label();
        HBox songInfoContainer = new HBox();
        StackPane imageContainer = new StackPane();
        ImageView songImg = new ImageView();
        VBox songInfo = new VBox();
        Label songName = new Label();
        Label artistName = new Label();
        Label albumName = new Label();
        Label duration = new Label();

        wrapper.setPrefWidth(200);
        wrapper.setPrefHeight(60);
        wrapper.setPadding(new Insets(0, 0, 0, 30));
        wrapper.getStyleClass().add("song-item");

        songId.setPrefWidth(35);
        songId.setPrefHeight(60);
        songId.getStyleClass().add("song-info");
        songId.setText((String.valueOf(id)));

        songInfoContainer.setPrefWidth(628);
        songInfoContainer.setPrefHeight(60);

        imageContainer.setPrefWidth(60);
        imageContainer.setPrefHeight(60);
        imageContainer.setPadding(new Insets(8));
        HBox.setMargin(imageContainer, new Insets(0, 14, 0, 0));
        songImg.setFitWidth(44);
        songImg.setFitHeight(44);
        songImg.setImage(new Image(this.getClass().getResourceAsStream(imgUrl)));
        imageContainer.getChildren().add(songImg);

        songInfo.setPrefHeight(60);

        songName.setPrefHeight(30);
        songName.setText(songN);
        songName.getStyleClass().add("song-name");
        songName.setPadding(new Insets(10,0,0,0));
        artistName.setPrefHeight(30);
        artistName.setText(artistN);
        artistName.getStyleClass().add("artist-name");
        artistName.setPadding(new Insets(0,0,10,0));
        songInfo.getChildren().addAll(songName, artistName);

        songInfoContainer.getChildren().addAll(imageContainer, songInfo);

        albumName.setPrefWidth(342);
        albumName.setPrefHeight(60);
        albumName.setText(albumN);
        albumName.getStyleClass().add("song-info");

        duration.setPrefWidth(70);
        duration.setPrefHeight(60);
        duration.setText(time);
        duration.getStyleClass().add("song-info");

        wrapper.getChildren().addAll(songId, songInfoContainer,albumName,duration);

        return wrapper;
    }

//    Handle play, pause, shuffle, loop, change volume and progress music
    public HBox createMusicPlayerBar(String nameOfSong, String nameOfArtist) {
        // Create variable for UI
        HBox wrapper = new HBox();
        // ========================================
        HBox songInfoContainer = new HBox();

        StackPane songImgContainer = new StackPane();
        ImageView songImg = new ImageView(new Image(this.getClass().getResourceAsStream("395eb4a5e6aa04c8574425345cd816f5.894x894x1.png")));

        VBox songInfo = new VBox();
        Label songName = new Label();
        Label artistName = new Label();
        // ==========================================
        VBox playerControllerContainer = new VBox();

        HBox playerController = new HBox();
        Button shuffleBtn = new Button();
        Button prevBtn = new Button();
        Button playPauseBtn = new Button();
        Button nextBtn = new Button();
        Button loopBtn = new Button();
        ImageView shuffleImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-shuffle-28.png")));
        ImageView activeShuffleImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-active-shuffle-28.png")));
        ImageView prevImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-skip-to-start-28.png")));
        ImageView playImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-circled-play-36.png")));
        ImageView pauseImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-pause-button-36.png")));
        ImageView nextImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-end-28.png")));
        ImageView loopImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-update-left-rotation-28.png")));
        ImageView activeLoopImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-active-update-left-rotation-28.png")));

        HBox songProgressContainer = new HBox();
        Label timeStart = new Label();
        Label timeEnd = new Label();
        StackPane slidoProgressBar = new StackPane();
        Slider songSlider = new Slider();
        ProgressBar songProgressBar = new ProgressBar();
        // ===================================
        HBox volumeSettingContainer = new HBox();

        Button volumeBtn = new Button();
        ImageView volumeImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-voice-28.png")));
        ImageView muteImg = new ImageView(new Image(this.getClass().getResourceAsStream("Images/icons8-mute-28.png")));
        Slider volumeSlider = new Slider();

        // ====================================
        double btnSize = 28;
        double imgBtnSize = 20;
        double playPauseBtnSize = 36;
        double playPauseImgSize = 32;
        double progressBarHeightSize = 10;
        double progressBarWidthSize = prefWidthPlayerControllerContainer - 40 * 2;

        // Render UI
        wrapper.setPrefWidth(prefWidthApp);
        wrapper.setPrefHeight(prefHeightPlayer);
        wrapper.setLayoutX(0);
        wrapper.setLayoutY(prefHeightApp - prefHeightPlayer);
        wrapper.getStyleClass().add("music-media-player");

        songInfoContainer.setPrefWidth(prefWidthSongInfoCorner);
        songInfoContainer.setPrefHeight(prefHeightPlayer);
        songImgContainer.setPrefWidth(prefHeightPlayer);
        songImgContainer.setPrefHeight(prefHeightPlayer);
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
        playerControllerContainer.setPrefHeight(prefHeightPlayer);
        playerController.setPrefHeight(prefHeightPlayer / 2);
        songProgressContainer.setPrefHeight(prefHeightPlayer / 2);
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
        volumeSettingContainer.setPrefHeight(prefHeightPlayer);
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
        playPauseBtn.setOnAction(actionEvent -> playPauseSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider));
        nextBtn.setOnAction(actionEvent -> nextSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider));
        prevBtn.setOnAction(actionEvent -> prevSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider));
        trackProgress(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
        songSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                timeLine.stop();
                timeLine.getKeyFrames().clear();
                timeLine = null;
                double current = (mouseEvent.getX() / songSlider.getWidth()) * songSlider.getMax();
                double end = media.getDuration().toSeconds();
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(current));
                if (isRunning == false) {
                    playPauseSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
                } else
                    trackProgress(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
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
                loopSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
                isLoop = true;
                loopBtn.setGraphic(activeLoopImg);
            } else {
                isLoop = false;
                loopBtn.setGraphic(loopImg);
            }
        });

        shuffleBtn.setOnAction(actionEvent -> {
            if (isShuffle == false) {
                shuffleBtn.setGraphic(activeShuffleImg);
                isShuffle = true;
            } else {
                shuffleBtn.setGraphic(shuffleImg);
                isShuffle = false;
            }
        });

        return wrapper;
    }


    public void playPauseSong(Button playPauseBtn, ImageView playImg, ImageView pauseImg,
                              ProgressBar songProgressBar, Label timeStart, Label timeEnd, Slider songSlider) {
        if (isRunning == false) {
            mediaPlayer.play();
            mediaPlayer.setVolume(volumeValue);
            playPauseBtn.setGraphic(pauseImg);
            trackProgress(playPauseBtn, playImg, pauseImg,songProgressBar, timeStart, timeEnd, songSlider);
            isRunning = true;
        } else {
            mediaPlayer.pause();
            playPauseBtn.setGraphic(playImg);
            timeLine.pause();
            isRunning = false;
        }
    }

    public void nextSong(Button playPauseBtn, ImageView playImg, ImageView pauseImg,
                         ProgressBar songProgressBar, Label timeStart, Label timeEnd, Slider songSlider) {
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
        playPauseSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
    }

    public void prevSong(Button playPauseBtn, ImageView playImg, ImageView pauseImg,
                         ProgressBar songProgressBar, Label timeStart, Label timeEnd, Slider songSlider) {
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
        playPauseSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
    }

    public void autoNextSong(Button playPauseBtn, ImageView playImg, ImageView pauseImg, ProgressBar songProgressBar,
                             Label timeStart, Label timeEnd, double current, double end, Slider songSlider) {
        if (current == end && isLoop == false) {
            nextSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
        }
    }

    public void loopSong(Button playPauseBtn, ImageView playImg, ImageView pauseImg,
                         ProgressBar songProgressBar, Label timeStart, Label timeEnd, Slider songSlider) {
        if (isLoop == true) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    isRunning = false;
                    mediaPlayer.seek(Duration.ZERO);
                    playPauseSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, songSlider);
                }
            });
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

    public void trackProgress(Button playPauseBtn, ImageView playImg, ImageView pauseImg,
                              ProgressBar songProgressBar, Label timeStart, Label timeEnd, Slider songSlider) {
        timeLine = new Timeline(new KeyFrame(Duration.seconds(0.01), e -> {
            double current = mediaPlayer.getCurrentTime().toSeconds();
            double end = media.getDuration().toSeconds();

            String currentFormat = formatTime((int)Math.round(current));
            String endFormat = formatTime((int)Math.round(end));

            timeStart.setText(currentFormat);
            timeEnd.setText(endFormat);

            songProgressBar.setProgress(current / end);
            songSlider.setValue(current / end);

            autoNextSong(playPauseBtn, playImg, pauseImg, songProgressBar, timeStart, timeEnd, current, end, songSlider);
        }));
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
    }

    public void startPlaylist() {
        songs = new ArrayList<>();
        directory = new File("C:\\Users\\Admin\\IdeaProjects\\music-player-project\\src\\main\\resources\\ptit\\tvnkhanh\\musicplayerproject\\Musics");
        files = directory.listFiles();
        if (files != null) {
            songs.addAll(Arrays.asList(files));
        }
        songNumber = 0;
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

//    Center an image in ImageView
    public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
}
