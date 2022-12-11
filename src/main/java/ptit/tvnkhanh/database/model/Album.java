package ptit.tvnkhanh.database.model;

import javafx.beans.property.*;

import java.sql.Date;

public class Album {
    private IntegerProperty albumId;
    private StringProperty name;
    private StringProperty imgURI;
    private ObjectProperty<Date> releaseDate;
    private StringProperty single;
    private IntegerProperty artistId;

    public Album() {
    }

    public Album(int albumId,String name, String imgURI, Date releaseDate,String single, int artistId) {
        this.albumId = new SimpleIntegerProperty(albumId);
        this.name = new SimpleStringProperty(name);
        this.imgURI = new SimpleStringProperty(imgURI);
        this.releaseDate = new SimpleObjectProperty<>(releaseDate);
        this.single = new SimpleStringProperty(single);
        this.artistId = new SimpleIntegerProperty(artistId);
    }

    public int getAlbumId() {
        return this.albumId.get();
    }

    public void setAlbumId(int albumId) {
        this.albumId.set(albumId);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getImgURI() {
        return this.imgURI.get();
    }

    public void setImgURI(String imgURI) {
        this.imgURI.set(imgURI);
    }

    public Date getReleaseDate() {
        return this.releaseDate.get();
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    public String getSingle() {
        return this.single.get();
    }

    public void setSingle(String single) {
        this.single.set(single);
    }

    public int getArtistId() {
        return this.artistId.get();
    }

    public void setArtistId(int artistId) {
        this.artistId.set(artistId);
    }

    public IntegerProperty albumIdProperty() {
        return albumId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty imgURIProperty() {
        return imgURI;
    }

    public StringProperty singleProperty() {
        return single;
    }

    public IntegerProperty artistIdProperty() {
        return artistId;
    }

    public ObjectProperty<Date> releaseDateProperty() {
        return releaseDate;
    }
}
