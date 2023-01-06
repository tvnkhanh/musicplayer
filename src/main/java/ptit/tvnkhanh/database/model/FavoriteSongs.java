package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FavoriteSongs {
    private IntegerProperty userId;
    private IntegerProperty trackId;

    public FavoriteSongs() {
    }

    public FavoriteSongs(int userId, int trackId) {
        this.userId = new SimpleIntegerProperty(userId);
        this.trackId = new SimpleIntegerProperty(trackId);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
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
