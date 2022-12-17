package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<User> getAllUser() throws Exception {
        List<User> lstUser = new ArrayList<>();
        String sql = "select * from [User]";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                User user = new User(rs.getNString("USERNAME"), rs.getString("PASSWORD"), rs.getString("ROLE"),
                        rs.getNString("EMAIL"), rs.getString("AVATAR_LINK"));

                lstUser.add(user);
            }
            return lstUser;
        }
    }

    public User getUser(String username, String password) throws Exception {
        String sql = "select * from [USER] where USERNAME = '" + username +"' and PASSWORD = '" + password + "'";
        User user = new User();
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                user = new User(rs.getNString("USERNAME"), rs.getString("PASSWORD"), rs.getString("ROLE"),
                        rs.getNString("EMAIL"), rs.getString("AVATAR_LINK"));
            }
            return user;
        }
    }

    public boolean insertUser(User user) throws Exception {
        String sql = "insert into [User](USERNAME, PASSWORD, ROLE, EMAIL, AVATAR_LINK) values (?,?,?,?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setNString(1, user.getUserName());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getRole());
            pstm.setNString(4, user.getEmail());
            pstm.setString(5, user.getAvatar_link());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateUser(User user) throws Exception {
        String sql = "Update [User] set PASSWORD=? where USERNAME=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, user.getPassword());
            pstm.setNString(2, user.getUserName());

            return pstm.executeUpdate() > 0;
        }

    }
}
