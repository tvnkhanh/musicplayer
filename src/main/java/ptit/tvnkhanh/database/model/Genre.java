package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Genre {
    private IntegerProperty genreId;
    private StringProperty title;

    public Genre() {
    }

    public Genre(int genreId, String title) {
        this.genreId = new SimpleIntegerProperty(genreId);
        this.title = new SimpleStringProperty(title);
    }

    public int getGenreId() {
        return this.genreId.get();
    }

    public void setGenreId(int genreId) {
        this.genreId.set(genreId);
    }

    public String getTitle() {
        return this.title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public IntegerProperty genreIdProperty() {
        return genreId;
    }

    public StringProperty titleProperty() {
        return title;
    }
}
