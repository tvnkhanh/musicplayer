package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Playlist {
    private StringProperty title;
    private StringProperty imageURI;
    private IntegerProperty userId;

    public Playlist() {
    }

    public Playlist(String title, String imageURI, int userId) {
        this.title = new SimpleStringProperty(title);
        this.imageURI = new SimpleStringProperty(imageURI);
        this.userId = new SimpleIntegerProperty(userId);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getImageURI() {
        return imageURI.get();
    }

    public StringProperty imageURIProperty() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI.set(imageURI);
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
}
