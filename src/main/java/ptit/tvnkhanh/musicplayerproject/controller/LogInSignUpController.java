package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ptit.tvnkhanh.database.DAO.UserDAO;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LogInSignUpController extends Controller implements Initializable {
    @FXML
    private TextField usernameLogin, passwordLogin;
    @FXML
    private TextField usernameSignUp, passwordSignUp, emailSignUp;
    @FXML
    private Label invalidLogin;
    @FXML
    private Label invalidSignUp;
    private UserDAO userDAO = new UserDAO();
    public static User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void loginAction(ActionEvent e) throws SQLException {
        if (usernameLogin.getText().isBlank() == false && passwordLogin.getText().isBlank() == false) {
            validateLogin(e);
        } else {
            invalidLogin.setText("Please enter username and password!");
            invalidLogin.setVisible(true);
        }
    }

    @FXML
    public void signupAction(ActionEvent e) throws SQLException {
        if (usernameSignUp.getText().isBlank() == false && passwordSignUp.getText().isBlank() == false
                            && emailSignUp.getText().isBlank() == false) {
            validateSignUp(e);
        } else {
            invalidSignUp.setText("Please enter email, password and username!");
            invalidSignUp.setVisible(true);
        }
    }

    public void validateLogin(ActionEvent e) throws SQLException {
        Connection con = DatabaseHelper.openConnection();
        String verifyLogin = "SELECT count(1) FROM [USER] WHERE USERNAME = '" + usernameLogin.getText() + "' AND PASSWORD = '" +
                passwordLogin.getText() + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    userDAO = new UserDAO();
                    currentUser = userDAO.getUser(usernameLogin.getText(), passwordLogin.getText());
                    switchToHomePage(e);
                } else {
                    invalidLogin.setText("Wrong username or password!");
                    invalidLogin.setVisible(true);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void validateSignUp(ActionEvent e) throws SQLException {
        Connection con = DatabaseHelper.openConnection();
        String verifySignUp = "SELECT count(1) FROM [USER] WHERE USERNAME = '" + usernameSignUp.getText() + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifySignUp);

            while (queryResult.next()) {
                if (queryResult.getInt(1) != 1) {
                    User user = new User(usernameSignUp.getText(), passwordSignUp.getText(), "user", emailSignUp.getText(), null);
                    userDAO.insertUser(user);
                    switchToLogInPage(e);
                } else {
                    invalidSignUp.setText("This username is already exists, please select the others!");
                    invalidSignUp.setVisible(true);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
