package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.FavoriteSongs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteSongsDAO {
    public List<String> getFavoriteSongs(int userId) throws SQLException {
        List<String> favoriteSongs = new ArrayList<>();
        try (
                Connection con = DatabaseHelper.openConnection();
                CallableStatement stmt = con.prepareCall("{call SP_LIKED_SONG(?)}");

        ) {
            stmt.setInt(1, userId);
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    favoriteSongs.add(rs.getNString("NAME"));
                }
            }
            return favoriteSongs;
        }
    }

    public boolean insertFavoriteSongs(FavoriteSongs favoriteSongs) throws Exception {
        String sql = "insert into Favorite_Songs(USER_ID, TRACK_ID) values (?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, favoriteSongs.getUserId());
            pstm.setInt(2, favoriteSongs.getTrackId());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean deleteFavoriteSongs(FavoriteSongs favoriteSongs) throws Exception {
        String sql = "delete from Favorite_Songs where USER_ID= ? AND TRACK_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, favoriteSongs.getUserId());
            pstm.setInt(2, favoriteSongs.getTrackId());
            return pstm.executeUpdate() > 0;
        }

    }
}
