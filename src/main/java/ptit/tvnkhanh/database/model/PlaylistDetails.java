package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlaylistDetails {
    private IntegerProperty playlistId;
    private IntegerProperty trackId;

    public PlaylistDetails() {
    }

    public PlaylistDetails(int playlistId, int trackId) {
        this.playlistId = new SimpleIntegerProperty(playlistId);
        this.trackId = new SimpleIntegerProperty(trackId);
    }

    public int getPlaylistId() {
        return playlistId.get();
    }

    public IntegerProperty playlistIdProperty() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId.set(playlistId);
    }

    public int getTrackId() {
        return trackId.get();
    }

    public IntegerProperty trackIdProperty() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId.set(trackId);
    }
}
