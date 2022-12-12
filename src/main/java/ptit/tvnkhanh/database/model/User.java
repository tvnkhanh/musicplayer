package ptit.tvnkhanh.database.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private StringProperty userName;
    private StringProperty password;
    private StringProperty role;
    private StringProperty email;
    private StringProperty avatar_link;

    public User() {
    }

    public User(String userName, String password, String role, String email, String avatar_link) {
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
        this.email = new SimpleStringProperty(email);
        this.avatar_link = new SimpleStringProperty(avatar_link);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getAvatar_link() {
        return avatar_link.get();
    }

    public StringProperty avatar_linkProperty() {
        return avatar_link;
    }

    public void setAvatar_link(String avatar_link) {
        this.avatar_link.set(avatar_link);
    }
}
