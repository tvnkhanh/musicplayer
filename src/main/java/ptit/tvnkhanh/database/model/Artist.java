package ptit.tvnkhanh.database.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Artist {
    private IntegerProperty artistId;
    private StringProperty name;
    private StringProperty country;

    public Artist() {
    }

    public Artist(int artistId, String name, String country) {
        this.artistId = new SimpleIntegerProperty(artistId);
        this.name = new SimpleStringProperty(name);
        this.country = new SimpleStringProperty(country);
    }

    public int getArtistId() {
        return this.artistId.get();
    }

    public void setArtistId(int artistId) {
        this.artistId.set(artistId);
    }

    public IntegerProperty artistIdProperty() {
        return artistId;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty countryProperty() {
        return country;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCountry() {
        return this.country.get();
    }

    public void setCountry(String country) {
        this.country.set(country);
    }
}
