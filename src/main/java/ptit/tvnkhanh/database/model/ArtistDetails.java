package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ArtistDetails {
    private IntegerProperty artistId;
    private IntegerProperty albumId;

    public ArtistDetails() {
    }

    public ArtistDetails(int artistId, int albumId) {
        this.artistId = new SimpleIntegerProperty(artistId);
        this.albumId = new SimpleIntegerProperty(albumId);
    }

    public int getArtistId() {
        return artistId.get();
    }

    public IntegerProperty artistIdProperty() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId.set(artistId);
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
