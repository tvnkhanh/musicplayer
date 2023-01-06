package ptit.tvnkhanh.musicplayerproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ptit.tvnkhanh.database.DAO.UserDAO;
import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.User;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LogInController extends Controller implements Initializable {
    @FXML
    private TextField usernameLogin= new TextField();
    @FXML
    private TextField passwordLogin= new TextField();
    @FXML
    private Label invalidLogin;
    @FXML
    private Button loginBtn;
    private UserDAO userDAO = new UserDAO();
    public static User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginBtn.setOnAction((e) -> {
            try {
                loginAction(e, null);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        setEventEnterKey(usernameLogin);
        setEventEnterKey(passwordLogin);
    }

    public void loginAction(ActionEvent e, KeyEvent keyEvent) throws SQLException {
        if (usernameLogin.getText().isBlank() == false && passwordLogin.getText().isBlank() == false) {
            validateLogin(e, keyEvent);
        } else {
            invalidLogin.setText("Please enter username and password!");
            invalidLogin.setVisible(true);
        }
    }

    public void validateLogin(ActionEvent e, KeyEvent keyEvent) throws SQLException {
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
                    if (e == null) {
                        switchToHomePageKeyEvent(keyEvent);
                    } else {
                        switchToHomePage(e);
                    }
                } else {
                    invalidLogin.setText("Wrong username or password!");
                    invalidLogin.setVisible(true);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setEventEnterKey(TextField textField) {
        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    loginAction(null, keyEvent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
