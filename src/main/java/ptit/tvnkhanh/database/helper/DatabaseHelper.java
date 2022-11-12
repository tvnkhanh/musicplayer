package ptit.tvnkhanh.database.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper {
    public static Connection openConnection() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=MUSIC_PLAYER;user=sa;password=2042002;encrypt=false";
        Connection connection = DriverManager.getConnection(connectionUrl);
        return connection;
    }
}
