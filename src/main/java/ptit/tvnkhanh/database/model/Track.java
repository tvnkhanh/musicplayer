package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Track {
    private IntegerProperty trackId;
    private StringProperty title;
    private StringProperty fileURI;
    private IntegerProperty numView;
    private IntegerProperty albumId;

    public Track() {
    }

    public Track(int trackId,String title, String fileURI, int numView, int albumId) {
        this.trackId = new SimpleIntegerProperty(trackId);
        this.title = new SimpleStringProperty(title);
        this.fileURI = new SimpleStringProperty(fileURI);
        this.numView = new SimpleIntegerProperty(numView);
        this.albumId = new SimpleIntegerProperty(albumId);
    }

    public int getTrackId() {
        return trackId.get();
    }

    public void setTrackId(int trackId) {
        this.trackId.set(trackId);
    }
    public String getTitle() {
        return this.title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getFileURI() {
        return this.fileURI.get();
    }

    public void setFileURI(String fileURI) {
        this.fileURI.set(fileURI);
    }

    public int getNumView() {
        return this.numView.get();
    }

    public void setNumView(int numView) {
        this.numView.set(numView);
    }

    public int getAlbumId() {
        return this.albumId.get();
    }

    public void setAlbumId(int albumId) {
        this.albumId.set(albumId);
    }

    public IntegerProperty trackIdProperty() {
        return trackId;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty fileURIProperty() {
        return fileURI;
    }

    public IntegerProperty numViewProperty() {
        return numView;
    }

    public IntegerProperty albumIdProperty() {
        return albumId;
    }
}
