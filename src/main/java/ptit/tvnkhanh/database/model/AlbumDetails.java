package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AlbumDetails {
    private IntegerProperty genreId;
    private IntegerProperty albumId;

    public AlbumDetails() {
    }

    public AlbumDetails(int albumId, int genreId) {
        this.albumId = new SimpleIntegerProperty(albumId);
        this.genreId = new SimpleIntegerProperty(genreId);
    }

    public int getGenreId() {
        return genreId.get();
    }

    public IntegerProperty genreIdProperty() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId.set(genreId);
    }

    public int getAlbumId() {
        return albumId.get();
    }

    public IntegerProperty albumIdProperty() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId.set(albumId);
    }
}
