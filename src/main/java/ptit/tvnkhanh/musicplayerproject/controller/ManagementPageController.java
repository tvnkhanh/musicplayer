package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import ptit.tvnkhanh.database.DAO.*;
import ptit.tvnkhanh.database.model.*;
import ptit.tvnkhanh.musicplayerproject.view.PlayerBar;

import java.io.File;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManagementPageController extends Controller implements Initializable {
    private PlayerBar playerBar = new PlayerBar();
    private final String pattern = "yyyy-MM-dd";

    @FXML
    private Button fileLinkChooser;

    @FXML
    private AnchorPane wrapper;
    @FXML
    private Button imgLinkChooser;
    @FXML
    private DatePicker releaseDateBtn;
    @FXML
    private ComboBox isSingle;
    @FXML
    private TextField albumIdText;
    @FXML
    private TextField trackName;
    @FXML
    private TextField albumName;
    @FXML
    private TextField artistIdText;
    @FXML
    private TextField artistName;
    @FXML
    private TextField countryName;
    @FXML
    private TextField artistIdPK;
    @FXML
    private TextField albumIdPK;
    @FXML
    private TextField trackIDPK;
    @FXML
    private TextField genreIDPK;
    @FXML
    private TextField genreTitle;
    @FXML
    private TextField albumIdDetails;
    @FXML
    private TextField genreIdDetails;
    @FXML
    private TextField albumIdTxt;
    @FXML
    private TableView<Artist> artistTable = new TableView<Artist>();
    @FXML
    private TableView<Album> albumTable = new TableView<Album>();
    @FXML
    private TableView<Genre> genreTable = new TableView<>();
    @FXML
    private MenuButton userManagement;
    @FXML
    private Button managementBtn;
    @FXML
    private ImageView userAvatar;
    @FXML
    private TableView<ArtistDetails> artistDetailsTable = new TableView<>();
    @FXML
    private TableView<AlbumDetails> albumDetailsTable = new TableView<>();
    @FXML
    private TableView<Track> trackTable = new TableView<Track>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkUser(userManagement, managementBtn, userAvatar);

        releaseDateBtn.setConverter(converter);
        releaseDateBtn.setPromptText(pattern.toLowerCase());
        isSingle.getItems().addAll("true", "false");
        isSingle.getSelectionModel().selectLast();

        try {
            createArtistTable();
            createAlbumTable();
            createArtistDetailsTable();
            createGenreTable();
            createAlbumDetailsTable();
            createTrackTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        // Listen change select event in artist table
        TableView.TableViewSelectionModel<Artist> selectionArtistModel = artistTable.getSelectionModel();
        ObservableList<Artist> selectedArtistItems = selectionArtistModel.getSelectedItems();
        selectedArtistItems.addListener(new ListChangeListener<Artist>() {
            @Override
            public void onChanged(Change<? extends Artist> change) {
                artistIdPK.setText(String.valueOf(selectedArtistItems.get(0).getArtistId()));
                artistName.setText(selectedArtistItems.get(0).getName());
                countryName.setText(selectedArtistItems.get(0).getCountry());
            }
        });

        // Listen change select event in album table
        TableView.TableViewSelectionModel<Album> selectionAlbumModel = albumTable.getSelectionModel();
        ObservableList<Album> selectedAlbumItems = selectionAlbumModel.getSelectedItems();
        selectedAlbumItems.addListener(new ListChangeListener<Album>() {
            @Override
            public void onChanged(Change<? extends Album> change) {
                albumIdPK.setText(String.valueOf(selectedAlbumItems.get(0).getAlbumId()));
                albumName.setText(selectedAlbumItems.get(0).getName());
                isSingle.setValue(selectedAlbumItems.get(0).getSingle());
                imgLinkChooser.setText(selectedAlbumItems.get(0).getImgURI());
                releaseDateBtn.setValue(selectedAlbumItems.get(0).getReleaseDate().toLocalDate());
            }
        });

        // Listen change select event in artist details table
        TableView.TableViewSelectionModel<ArtistDetails> selectionArtistDetailsModel = artistDetailsTable.getSelectionModel();
        ObservableList<ArtistDetails> selectedArtistDetailsItems = selectionArtistDetailsModel.getSelectedItems();
        selectedArtistDetailsItems.addListener(new ListChangeListener<ArtistDetails>() {
            @Override
            public void onChanged(Change<? extends ArtistDetails> change) {
                artistIdText.setText(String.valueOf(selectedArtistDetailsItems.get(0).getArtistId()));
                albumIdTxt.setText(String.valueOf(selectedArtistDetailsItems.get(0).getAlbumId()));
            }
        });

        // Listen change select event in genre table
        TableView.TableViewSelectionModel<Genre> selectionGenreModel = genreTable.getSelectionModel();
        ObservableList<Genre> selectedGenreItems = selectionGenreModel.getSelectedItems();
        selectedGenreItems.addListener(new ListChangeListener<Genre>() {
            @Override
            public void onChanged(Change<? extends Genre> change) {
                genreIDPK.setText(String.valueOf(selectedGenreItems.get(0).getGenreId()));
                genreTitle.setText(selectedGenreItems.get(0).getTitle());
            }
        });

        // Listen change select event in album details table
        TableView.TableViewSelectionModel<AlbumDetails> selectionAlbumDetailsModel = albumDetailsTable.getSelectionModel();
        ObservableList<AlbumDetails> selectedAlbumDetailsItems = selectionAlbumDetailsModel.getSelectedItems();
        selectedAlbumDetailsItems.addListener(new ListChangeListener<AlbumDetails>() {
            @Override
            public void onChanged(Change<? extends AlbumDetails> change) {
                albumIdDetails.setText(String.valueOf(selectedAlbumDetailsItems.get(0).getAlbumId()));
                genreIdDetails.setText(String.valueOf(selectedAlbumDetailsItems.get(0).getGenreId()));
            }
        });

        // Listen change select event in track table
        TableView.TableViewSelectionModel<Track> selectionTrackModel = trackTable.getSelectionModel();
        ObservableList<Track> selectedTrackItems = selectionTrackModel.getSelectedItems();
        selectedTrackItems.addListener(new ListChangeListener<Track>() {
            @Override
            public void onChanged(Change<? extends Track> change) {
                trackIDPK.setText(String.valueOf(selectedTrackItems.get(0).getTrackId()));
                trackName.setText(selectedTrackItems.get(0).getTitle());
                fileLinkChooser.setText(selectedTrackItems.get(0).getFileURI());
                albumIdText.setText(String.valueOf(selectedTrackItems.get(0).getAlbumId()));
            }
        });

        HBox renderPlayerBar = playerBar.createMusicPlayerBar();
        wrapper.getChildren().add(renderPlayerBar);
    }

    @FXML
    public void selectFile(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3 file", "*.mp3", "*m4a"));
        File f = fc.showOpenDialog(null);

        if (f != null) {
            fileLinkChooser.setText(f.toPath().toString());
        }
    }

    @FXML
    public void selectImgFile(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("img file", "*.png", "*jpg"));
        File f = fc.showOpenDialog(null);

        if (f != null) {
            imgLinkChooser.setText(f.toPath().toString());
        }
    }

    public StringConverter converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        @Override
        public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }
        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    };

    // Create table for artist table
    public void createArtistTable() throws Exception {
        TableColumn<Artist, Integer> artistIdCol = new TableColumn<Artist, Integer>("Artist Id");
        TableColumn<Artist, String> artistNameCol = new TableColumn<Artist, String>("Name");
        TableColumn<Artist, String> artistCountryCol = new TableColumn<Artist, String>("Country");

        artistIdCol.setCellValueFactory(data -> data.getValue().artistIdProperty().asObject());
        artistNameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        artistCountryCol.setCellValueFactory(data -> data.getValue().countryProperty());

        artistIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Artist> list = getArtistList();
        artistTable.setItems(list);

        artistTable.getColumns().addAll(artistIdCol, artistNameCol, artistCountryCol);
    }

    private ObservableList<Artist> getArtistList() throws Exception {
        ArtistDAO artistDAO = new ArtistDAO();
        ObservableList<Artist> list = FXCollections.observableArrayList(artistDAO.getAllArtist());
        return list;
    }

    // Create table for album table
    public void createAlbumTable() throws Exception {
        TableColumn<Album, Integer> albumIdCol = new TableColumn<Album, Integer>("Album Id");
        TableColumn<Album, String> albumNameCol = new TableColumn<Album, String>("Name");
        TableColumn<Album, String> imgURICol = new TableColumn<Album, String>("Image Link");
        TableColumn<Album, Date> dateCol = new TableColumn<Album, Date>("Release Date");
        TableColumn<Album, String> singleCol = new TableColumn<Album, String>("Single");

        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asObject());
        albumNameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        imgURICol.setCellValueFactory(data -> data.getValue().imgURIProperty());
        dateCol.setCellValueFactory(data -> data.getValue().releaseDateProperty());
        singleCol.setCellValueFactory(data -> data.getValue().singleProperty());


        albumIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Album> list = getAlbumList();
        albumTable.setItems(list);

        albumTable.getColumns().addAll(albumIdCol, albumNameCol, imgURICol, dateCol, singleCol);
    }

    private ObservableList<Album> getAlbumList() throws Exception {
        AlbumDAO albumDAO = new AlbumDAO();
        ObservableList<Album> list = FXCollections.observableArrayList(albumDAO.getAllAlbum());
        return list;
    }

    // Create table for artist details table
    public void createArtistDetailsTable() throws Exception {
        TableColumn<ArtistDetails, Integer> artistIdCol = new TableColumn<ArtistDetails, Integer>("Artist Id");
        TableColumn<ArtistDetails, String> albumIdCol = new TableColumn<ArtistDetails, String>("Album Id");

        artistIdCol.setCellValueFactory(data -> data.getValue().artistIdProperty().asObject());
        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asString());

        artistIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<ArtistDetails> list = getArtistDetailsList();
        artistDetailsTable.setItems(list);

        artistDetailsTable.getColumns().addAll(artistIdCol, albumIdCol);
    }

    private ObservableList<ArtistDetails> getArtistDetailsList() throws Exception {
        ArtistDetailsDAO artistDetailsDAO = new ArtistDetailsDAO();
        ObservableList<ArtistDetails> list = FXCollections.observableArrayList(artistDetailsDAO.getAllArtistDetails());
        return list;
    }

    // Create table for genre table
    public void createGenreTable() throws Exception {
        TableColumn<Genre, Integer> genreIdCol = new TableColumn<Genre, Integer>("Genre Id");
        TableColumn<Genre, String> genreTitleCol = new TableColumn<Genre, String>("Name");

        genreIdCol.setCellValueFactory(data -> data.getValue().genreIdProperty().asObject());
        genreTitleCol.setCellValueFactory(data -> data.getValue().titleProperty());

        genreIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Genre> list = getGenreList();
        genreTable.setItems(list);

        genreTable.getColumns().addAll(genreIdCol, genreTitleCol);
    }

    private ObservableList<Genre> getGenreList() throws Exception {
        GenreDAO genreDAO = new GenreDAO();
        ObservableList<Genre> list = FXCollections.observableArrayList(genreDAO.getAllGenre());
        return list;
    }

    // Create table for album details table
    public void createAlbumDetailsTable() throws Exception {
        TableColumn<AlbumDetails, Integer> albumIdCol = new TableColumn<AlbumDetails, Integer>("Album Id");
        TableColumn<AlbumDetails, String> genreIdCol = new TableColumn<AlbumDetails, String>("Genre Id");

        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asObject());
        genreIdCol.setCellValueFactory(data -> data.getValue().genreIdProperty().asString());

        albumIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<AlbumDetails> list = getAlbumDetailsList();
        albumDetailsTable.setItems(list);

        albumDetailsTable.getColumns().addAll(albumIdCol, genreIdCol);
    }

    private ObservableList<AlbumDetails> getAlbumDetailsList() throws Exception {
        AlbumDetailsDAO albumDetailsDAO = new AlbumDetailsDAO();
        ObservableList<AlbumDetails> list = FXCollections.observableArrayList(albumDetailsDAO.getAllAlbumDetails());
        return list;
    }

    // Create table for track table
    public void createTrackTable() throws Exception {
        TableColumn<Track, Integer> trackIdCol = new TableColumn<Track, Integer>("Track Id");
        TableColumn<Track, String> trackTitleCol = new TableColumn<Track, String>("Name");
        TableColumn<Track, String> fileURICol = new TableColumn<Track, String>("File Link");
        TableColumn<Track, Integer> viewCol = new TableColumn<Track, Integer>("View");
        TableColumn<Track, Integer> albumIdCol = new TableColumn<Track, Integer>("Album Id");

        trackIdCol.setCellValueFactory(data -> data.getValue().trackIdProperty().asObject());
        trackTitleCol.setCellValueFactory(data -> data.getValue().titleProperty());
        fileURICol.setCellValueFactory(data -> data.getValue().fileURIProperty());
        viewCol.setCellValueFactory(data -> data.getValue().numViewProperty().asObject());
        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asObject());

        trackIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Track> list = getTrackList();
        trackTable.setItems(list);

        trackTable.getColumns().addAll(trackIdCol, trackTitleCol, fileURICol, viewCol, albumIdCol);
    }

    private ObservableList<Track> getTrackList() throws Exception {
        TrackDAO trackDAO = new TrackDAO();
        ObservableList<Track> list = FXCollections.observableArrayList(trackDAO.getAllTrack());
        return list;
    }

    // Alert methods
    public Optional<ButtonType> confirmationAlert(String logicHandle, String tabName) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(logicHandle.substring(0, 1).toUpperCase() + logicHandle.substring(1) + " " +
                tabName.substring(0, 1).toUpperCase() + tabName.substring(1));
        if (Objects.equals(logicHandle, "remove") || Objects.equals(logicHandle, "search")) {
            confirmationAlert.setContentText("Are you sure to " + logicHandle + " this " + tabName + " from the database?");
        }
        else {
            confirmationAlert.setContentText("Are you sure to " + logicHandle + " this " + tabName + " to the database?");
        }
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    // Handle event for artist table
    public Artist createArtistObject() {
        Artist artist = new Artist(Integer.parseInt(artistIdPK.getText()) ,artistName.getText(), countryName.getText());
        return artist;
    }

    public void renderSearchArtistResult(Artist artist) throws Exception {
        List<Artist> lst = new ArrayList<>();
        lst.add(artist);

        TableColumn<Artist, Integer> artistIdCol = new TableColumn<Artist, Integer>("Artist Id");
        TableColumn<Artist, String> artistNameCol = new TableColumn<Artist, String>("Name");
        TableColumn<Artist, String> artistCountryCol = new TableColumn<Artist, String>("Country");

        artistIdCol.setCellValueFactory(data -> data.getValue().artistIdProperty().asObject());
        artistNameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        artistCountryCol.setCellValueFactory(data -> data.getValue().countryProperty());

        artistIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Artist> list = FXCollections.observableArrayList(lst);
        artistTable.setItems(list);

        artistTable.getColumns().addAll(artistIdCol, artistNameCol, artistCountryCol);
    }

    public void errorArtistAlert() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setContentText("There was an error!");
        errorAlert.showAndWait();
    }

    public void resetArtistTextFields() {
        artistIdPK.setText("");
        artistName.setText("");
        countryName.setText("");
    }

    public void addArtist() throws Exception {
        ArtistDAO artistDAO = new ArtistDAO();
        Artist artist = createArtistObject();
        Optional<ButtonType> result = confirmationAlert("add", "artist");
        if (result.get() == ButtonType.OK) {
            artistDAO.insertArtist(artist);

            artistTable.getColumns().clear();
            createArtistTable();
            resetArtistTextFields();
        }
    }

    public void removeArtist() throws Exception {
        Optional<ButtonType> result = confirmationAlert("remove", "artist");
        if (result.get() == ButtonType.OK) {
            Artist artist = createArtistObject();
            ArtistDAO artistDAO = new ArtistDAO();
            artistDAO.deleteArtist(artist.getArtistId());

            artistTable.getColumns().clear();
            createArtistTable();
            resetArtistTextFields();
        }
    }

    public void editArtist() throws Exception {
        Optional<ButtonType> result = confirmationAlert("edit", "artist");
        if (result.get() == ButtonType.OK) {
            Artist artist = createArtistObject();
            ArtistDAO artistDAO = new ArtistDAO();
            artistDAO.updateArtist(artist);

            artistTable.getColumns().clear();
            createArtistTable();
            resetArtistTextFields();
        }
    }

    public void searchArtist() throws Exception {
        ArtistDAO artistDAO = new ArtistDAO();
        if (artistIdPK.getText() != "") {
            Artist artist = artistDAO.findArtist(Integer.parseInt(artistIdPK.getText()));
            if (artist != null) {
                artistTable.getColumns().clear();
                renderSearchArtistResult(artist);
            } else {
                errorArtistAlert();
                artistTable.getColumns().clear();
                createArtistTable();
            }
        } else if (artistName.getText() != null) {
            Artist artist = artistDAO.findArtist(artistName.getText());
            if (artist != null) {
                artistTable.getColumns().clear();
                renderSearchArtistResult(artist);
            } else {
                errorArtistAlert();
                artistTable.getColumns().clear();
                createArtistTable();
            }
        }
    }

    // Handle event for album table
    public Album createAlbumObject() {
        Date releaseDate = null;

        if (releaseDateBtn.getValue() != null) {
            releaseDate = Date.valueOf(LocalDate.of(releaseDateBtn.getValue().getYear(),
                    releaseDateBtn.getValue().getMonth(), releaseDateBtn.getValue().getDayOfMonth()));
        }

        Album album = new Album(Integer.parseInt(albumIdPK.getText()), albumName.getText(), imgLinkChooser.getText(), releaseDate,
                isSingle.getValue().toString());
        return album;
    }

    public void resetAlbumTextFields() {
        albumIdPK.setText("");
        albumName.setText("");
        imgLinkChooser.setText("Select a image");
        releaseDateBtn.setValue(null);
        isSingle.getSelectionModel().selectLast();
    }

    public void renderSearchAlbumResult(Album album) {
        List<Album> lst = new ArrayList<>();
        lst.add(album);

        TableColumn<Album, Integer> albumIdCol = new TableColumn<Album, Integer>("Album Id");
        TableColumn<Album, String> albumNameCol = new TableColumn<Album, String>("Name");
        TableColumn<Album, String> imgURICol = new TableColumn<Album, String>("Image Link");
        TableColumn<Album, Date> dateCol = new TableColumn<Album, Date>("Release Date");
        TableColumn<Album, String> singleCol = new TableColumn<Album, String>("Single");

        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asObject());
        albumNameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        imgURICol.setCellValueFactory(data -> data.getValue().imgURIProperty());
        dateCol.setCellValueFactory(data -> data.getValue().releaseDateProperty());
        singleCol.setCellValueFactory(data -> data.getValue().singleProperty());


        albumIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Album> list = FXCollections.observableArrayList(lst);
        albumTable.setItems(list);

        albumTable.getColumns().addAll(albumIdCol, albumNameCol, imgURICol, dateCol, singleCol);
    }

    public void addAlbum() throws Exception {
        AlbumDAO albumDAO = new AlbumDAO();
        Album album = createAlbumObject();
        Optional<ButtonType> result = confirmationAlert("add", "album");
        if (result.get() == ButtonType.OK) {
            albumDAO.insertAlbum(album);

            albumTable.getColumns().clear();
            createAlbumTable();
            resetAlbumTextFields();
        }
    }

    public void removeAlbum() throws Exception {
        Optional<ButtonType> result = confirmationAlert("remove", "album");
        if (result.get() == ButtonType.OK) {
            Album album = createAlbumObject();
            AlbumDAO albumDAO = new AlbumDAO();
            albumDAO.deleteAlbum(album.getAlbumId());

            albumTable.getColumns().clear();
            createAlbumTable();
            resetAlbumTextFields();
        }
    }

    public void editAlbum() throws Exception {
        Optional<ButtonType> result = confirmationAlert("edit", "album");
        if (result.get() == ButtonType.OK) {
            Album album = createAlbumObject();
            AlbumDAO albumDAO = new AlbumDAO();
            albumDAO.updateAlbum(album);

            albumTable.getColumns().clear();
            createAlbumTable();
            resetAlbumTextFields();
        }
    }

    public void searchAlbum() throws Exception {
        AlbumDAO albumDAO = new AlbumDAO();
        if (albumIdPK.getText() != "") {
            Album album = albumDAO.findAlbum(Integer.parseInt(albumIdPK.getText()));
            if (album != null) {
                albumTable.getColumns().clear();
                renderSearchAlbumResult(album);
            } else {
                errorArtistAlert();
                albumTable.getColumns().clear();
                createAlbumTable();
            }
        } else if (albumName.getText() != null) {
            Album album = albumDAO.findAlbum(albumName.getText());
            if (album != null) {
                albumTable.getColumns().clear();
                renderSearchAlbumResult(album);
            } else {
                errorArtistAlert();
                albumTable.getColumns().clear();
                createAlbumTable();
            }
        }
    }

    // Handle event for artist details table
    public ArtistDetails createArtistDetailsObject() {
        ArtistDetails artistDetails = new ArtistDetails(Integer.parseInt(artistIdText.getText()), Integer.parseInt(albumIdTxt.getText()));
        return artistDetails;
    }

    public void resetArtistDetailsTextFields() {
        artistIdText.setText("");
        albumIdTxt.setText("");
    }

    public void renderSearchArtistDetailsResult(ArtistDetails artistDetails) {
        List<ArtistDetails> lst = new ArrayList<>();
        lst.add(artistDetails);
        TableColumn<ArtistDetails, Integer> artistIdCol = new TableColumn<ArtistDetails, Integer>("Artist Id");
        TableColumn<ArtistDetails, String> albumIdCol = new TableColumn<ArtistDetails, String>("Album Id");

        artistIdCol.setCellValueFactory(data -> data.getValue().artistIdProperty().asObject());
        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asString());

        artistIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<ArtistDetails> list = FXCollections.observableArrayList(lst);
        artistDetailsTable.setItems(list);

        artistDetailsTable.getColumns().addAll(artistIdCol, albumIdCol);
    }

    public void addArtistDetails() throws Exception {
        ArtistDetailsDAO artistDetailsDAO = new ArtistDetailsDAO();
        ArtistDetails artistDetails = createArtistDetailsObject();
        Optional<ButtonType> result = confirmationAlert("add", "artist details");
        if (result.get() == ButtonType.OK) {
            artistDetailsDAO.insertArtistDetails(artistDetails);

            artistDetailsTable.getColumns().clear();
            createArtistDetailsTable();
            resetArtistDetailsTextFields();
        }
    }

    public void removeArtistDetails() throws Exception {
        Optional<ButtonType> result = confirmationAlert("remove", "artist details");
        if (result.get() == ButtonType.OK) {
            ArtistDetails artistDetails = createArtistDetailsObject();
            ArtistDetailsDAO artistDetailsDAO = new ArtistDetailsDAO();
            artistDetailsDAO.deleteArtistDetails(artistDetails.getArtistId(), artistDetails.getAlbumId());

            artistDetailsTable.getColumns().clear();
            createArtistDetailsTable();
            resetArtistDetailsTextFields();
        }
    }

    public void editArtistDetails() throws Exception {
        Optional<ButtonType> result = confirmationAlert("edit", "artist details");
        if (result.get() == ButtonType.OK) {
            ArtistDetails artistDetails = createArtistDetailsObject();
            ArtistDetailsDAO artistDetailsDAO = new ArtistDetailsDAO();
            artistDetailsDAO.updateArtistDetails(artistDetails);

            artistDetailsTable.getColumns().clear();
            createArtistDetailsTable();
            resetArtistDetailsTextFields();
        }
    }

    public void searchArtistDetails() throws Exception {
        ArtistDetailsDAO artistDetailsDAO = new ArtistDetailsDAO();
        if (artistIdText.getText() != "" && albumIdTxt.getText() != "") {
            ArtistDetails artistDetails = artistDetailsDAO.findArtistDetails(Integer.parseInt(artistIdText.getText()),
                    Integer.parseInt(albumIdTxt.getText()));
            if (artistDetails != null) {
                artistDetailsTable.getColumns().clear();
                renderSearchArtistDetailsResult(artistDetails);
            } else {
                errorArtistAlert();
                artistDetailsTable.getColumns().clear();
                createArtistDetailsTable();
            }
        } else {
            errorArtistAlert();
            artistDetailsTable.getColumns().clear();
            createArtistDetailsTable();
        }
    }

    // Handle event for genre table
    public Genre createGenreObject() {
        Genre genre = new Genre(Integer.parseInt(genreIDPK.getText()), genreTitle.getText());
        return genre;
    }

    public void resetGenreTextFields() {
        genreIDPK.setText("");
        genreTitle.setText("");
    }

    public void renderSearchGenreResult(Genre genre) {
        List<Genre> lst = new ArrayList<>();
        lst.add(genre);
        TableColumn<Genre, Integer> genreIdCol = new TableColumn<Genre, Integer>("Genre Id");
        TableColumn<Genre, String> genreTitleCol = new TableColumn<Genre, String>("Name");

        genreIdCol.setCellValueFactory(data -> data.getValue().genreIdProperty().asObject());
        genreTitleCol.setCellValueFactory(data -> data.getValue().titleProperty());

        genreIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Genre> list = FXCollections.observableArrayList(lst);
        genreTable.setItems(list);

        genreTable.getColumns().addAll(genreIdCol, genreTitleCol);
    }

    public void addGenre() throws Exception {
        GenreDAO genreDAO = new GenreDAO();
        Genre genre = createGenreObject();
        Optional<ButtonType> result = confirmationAlert("add", "genre");
        if (result.get() == ButtonType.OK) {
            genreDAO.insertGenre(genre);

            genreTable.getColumns().clear();
            createGenreTable();
            resetGenreTextFields();
        }
    }

    public void removeGenre() throws Exception {
        Optional<ButtonType> result = confirmationAlert("remove", "genre");
        if (result.get() == ButtonType.OK) {
            Genre genre = createGenreObject();
            GenreDAO genreDAO = new GenreDAO();
            genreDAO.deleteGenre(genre.getGenreId());

            genreTable.getColumns().clear();
            createGenreTable();
            resetGenreTextFields();
        }
    }

    public void editGenre() throws Exception {
        Optional<ButtonType> result = confirmationAlert("edit", "genre");
        if (result.get() == ButtonType.OK) {
            Genre genre = createGenreObject();
            GenreDAO genreDAO = new GenreDAO();
            genreDAO.updateGenre(genre);

            genreTable.getColumns().clear();
            createGenreTable();
            resetGenreTextFields();
        }
    }

    public void searchGenre() throws Exception {
        GenreDAO genreDAO = new GenreDAO();
        if (genreIDPK.getText() != "") {
            Genre genre = genreDAO.findGenre(Integer.parseInt(genreIDPK.getText()));
            if (genre != null) {
                genreTable.getColumns().clear();
                renderSearchGenreResult(genre);
            } else {
                errorArtistAlert();
                genreTable.getColumns().clear();
                createGenreTable();
            }
        } else if (genreTitle.getText() != null) {
            Genre genre = genreDAO.findGenre(genreTitle.getText());
            if (genre != null) {
                genreTable.getColumns().clear();
                renderSearchGenreResult(genre);
            } else {
                errorArtistAlert();
                genreTable.getColumns().clear();
                createGenreTable();
            }
        }
    }

    // Handle event for album details table
    public AlbumDetails createAlbumDetailsObject() {
        AlbumDetails albumDetails = new AlbumDetails(Integer.parseInt(albumIdDetails.getText()), Integer.parseInt(genreIdDetails.getText()));
        return albumDetails;
    }

    public void resetAlbumDetailsTextFields() {
        albumIdDetails.setText("");
        genreIdDetails.setText("");
    }

    public void renderSearchAlbumDetailsResult(AlbumDetails albumDetails) {
        List<AlbumDetails> lst = new ArrayList<>();
        lst.add(albumDetails);
        TableColumn<AlbumDetails, Integer> albumIdCol = new TableColumn<AlbumDetails, Integer>("Album Id");
        TableColumn<AlbumDetails, String> genreIdCol = new TableColumn<AlbumDetails, String>("Genre Id");

        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asObject());
        genreIdCol.setCellValueFactory(data -> data.getValue().genreIdProperty().asString());

        albumIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<AlbumDetails> list = FXCollections.observableArrayList(lst);
        albumDetailsTable.setItems(list);

        albumDetailsTable.getColumns().addAll(albumIdCol, genreIdCol);
    }

    public void addAlbumDetails() throws Exception {
        AlbumDetailsDAO albumDetailsDAO = new AlbumDetailsDAO();
        AlbumDetails albumDetails = createAlbumDetailsObject();
        Optional<ButtonType> result = confirmationAlert("add", "album details");
        if (result.get() == ButtonType.OK) {
            albumDetailsDAO.insertAlbumDetails(albumDetails);

            albumDetailsTable.getColumns().clear();
            createAlbumDetailsTable();
            resetAlbumDetailsTextFields();
        }
    }

    public void removeAlbumDetails() throws Exception {
        Optional<ButtonType> result = confirmationAlert("remove", "album details");
        if (result.get() == ButtonType.OK) {
            AlbumDetails albumDetails = createAlbumDetailsObject();
            AlbumDetailsDAO albumDetailsDAO = new AlbumDetailsDAO();
            albumDetailsDAO.deleteAlbumDetails(albumDetails.getAlbumId(), albumDetails.getGenreId());

            albumDetailsTable.getColumns().clear();
            createAlbumDetailsTable();
            resetAlbumDetailsTextFields();
        }
    }

    public void editAlbumDetails() throws Exception {
        Optional<ButtonType> result = confirmationAlert("edit", "album details");
        if (result.get() == ButtonType.OK) {
            AlbumDetails albumDetails = createAlbumDetailsObject();
            AlbumDetailsDAO albumDetailsDAO = new AlbumDetailsDAO();
            albumDetailsDAO.updateAlbumDetails(albumDetails);

            albumDetailsTable.getColumns().clear();
            createAlbumDetailsTable();
            resetAlbumDetailsTextFields();
        }
    }

    public void searchAlbumDetails() throws Exception {
        AlbumDetailsDAO albumDetailsDAO = new AlbumDetailsDAO();
        if (albumIdDetails.getText() != "" && genreIdDetails.getText() != "") {
            AlbumDetails albumDetails = albumDetailsDAO.findAlbumDetails(Integer.parseInt(albumIdDetails.getText()),
                    Integer.parseInt(genreIdDetails.getText()));
            if (albumDetails != null) {
                albumDetailsTable.getColumns().clear();
                renderSearchAlbumDetailsResult(albumDetails);
            } else {
                errorArtistAlert();
                albumDetailsTable.getColumns().clear();
                createAlbumDetailsTable();
            }
        }
        else {
            errorArtistAlert();
            albumDetailsTable.getColumns().clear();
            createAlbumDetailsTable();
        }
    }

    // Handle event for track table
    public Track createTrackObject() {
        Track track = new Track(Integer.parseInt(trackIDPK.getText()), trackName.getText(), fileLinkChooser.getText(),
                0, Integer.parseInt(albumIdText.getText()));
        return track;
    }

    public void resetTrackTextFields() {
        trackIDPK.setText("");
        trackName.setText("");
        fileLinkChooser.setText("Select a song");
        albumIdText.setText("");
    }

    public void renderSearchTrackResult(Track track) {
        List<Track> lst = new ArrayList<>();
        lst.add(track);
        TableColumn<Track, Integer> trackIdCol = new TableColumn<Track, Integer>("Track Id");
        TableColumn<Track, String> trackTitleCol = new TableColumn<Track, String>("Name");
        TableColumn<Track, String> fileURICol = new TableColumn<Track, String>("File Link");
        TableColumn<Track, Integer> viewCol = new TableColumn<Track, Integer>("View");
        TableColumn<Track, Integer> albumIdCol = new TableColumn<Track, Integer>("Album Id");

        trackIdCol.setCellValueFactory(data -> data.getValue().trackIdProperty().asObject());
        trackTitleCol.setCellValueFactory(data -> data.getValue().titleProperty());
        fileURICol.setCellValueFactory(data -> data.getValue().fileURIProperty());
        viewCol.setCellValueFactory(data -> data.getValue().numViewProperty().asObject());
        albumIdCol.setCellValueFactory(data -> data.getValue().albumIdProperty().asObject());

        trackIdCol.setSortType(TableColumn.SortType.ASCENDING);

        ObservableList<Track> list = FXCollections.observableArrayList(lst);
        trackTable.setItems(list);

        trackTable.getColumns().addAll(trackIdCol, trackTitleCol, fileURICol, viewCol, albumIdCol);
    }

    public void addTrack() throws Exception {
        TrackDAO trackDAO = new TrackDAO();
        Track track = createTrackObject();
        Optional<ButtonType> result = confirmationAlert("add", "track");
        if (result.get() == ButtonType.OK) {
            trackDAO.insertTrack(track);

            trackTable.getColumns().clear();
            createTrackTable();
            resetTrackTextFields();
        }
    }

    public void removeTrack() throws Exception {
        Optional<ButtonType> result = confirmationAlert("remove", "track");
        if (result.get() == ButtonType.OK) {
            Track track = createTrackObject();
            TrackDAO trackDAO = new TrackDAO();
            trackDAO.deleteTrack(track.getTrackId());

            trackTable.getColumns().clear();
            createTrackTable();
            resetTrackTextFields();
        }
    }

    public void editTrack() throws Exception {
        Optional<ButtonType> result = confirmationAlert("edit", "track");
        if (result.get() == ButtonType.OK) {
            Track track = createTrackObject();
            TrackDAO trackDAO = new TrackDAO();
            trackDAO.updateTrack(track);

            trackTable.getColumns().clear();
            createTrackTable();
            resetTrackTextFields();
        }
    }

    public void searchTrack() throws Exception {
        TrackDAO trackDAO = new TrackDAO();
        if (trackIDPK.getText() != "") {
            Track track = trackDAO.findTrack(Integer.parseInt(trackIDPK.getText()));
            if (track != null) {
                trackTable.getColumns().clear();
                renderSearchTrackResult(track);
            } else {
                errorArtistAlert();
                trackTable.getColumns().clear();
                createTrackTable();
            }
        } else if (trackName.getText() != null) {
            Track track = trackDAO.findTrack(trackName.getText());
            if (track != null) {
                trackTable.getColumns().clear();
                renderSearchTrackResult(track);
            } else {
                errorArtistAlert();
                trackTable.getColumns().clear();
                createTrackTable();
            }
        }
    }
}
