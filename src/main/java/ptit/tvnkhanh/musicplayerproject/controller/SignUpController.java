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

public class SignUpController extends Controller implements Initializable {
    @FXML
    private TextField usernameSignUp = new TextField();
    @FXML
    private TextField passwordSignUp = new TextField();
    @FXML
    private TextField emailSignUp = new TextField();
    @FXML
    private Label invalidSignUp;
    @FXML
    private Button signupBtn;
    private UserDAO userDAO = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signupBtn.setOnAction((e) -> {
            try {
                signupAction(e, null);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        setEventEnterKey(usernameSignUp);
        setEventEnterKey(passwordSignUp);
        setEventEnterKey(emailSignUp);
    }

    public void signupAction(ActionEvent e, KeyEvent keyEvent) throws SQLException {
        if (usernameSignUp.getText().isBlank() == false && passwordSignUp.getText().isBlank() == false
                && emailSignUp.getText().isBlank() == false) {
            validateSignUp(e, keyEvent);
        } else {
            invalidSignUp.setText("Please enter email, password and username!");
            invalidSignUp.setVisible(true);
        }
    }

    public void validateSignUp(ActionEvent e, KeyEvent keyEvent) throws SQLException {
        Connection con = DatabaseHelper.openConnection();
        String verifySignUp = "SELECT count(1) FROM [USER] WHERE USERNAME = '" + usernameSignUp.getText() + "'";
        try {
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(verifySignUp);

            while (queryResult.next()) {
                if (queryResult.getInt(1) != 1) {
                    User user = new User();
                    user.setUserName(usernameSignUp.getText());
                    user.setPassword(passwordSignUp.getText());
                    user.setRole("user");
                    user.setEmail(emailSignUp.getText());
                    userDAO.insertUser(user);
                    if (e != null) {
                        switchToLogInPage(e);
                    } else {
                        switchToLogInPage(keyEvent);
                    }
                } else {
                    invalidSignUp.setText("This username is already exists, please select the others!");
                    invalidSignUp.setVisible(true);
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
                    signupAction(null, keyEvent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
