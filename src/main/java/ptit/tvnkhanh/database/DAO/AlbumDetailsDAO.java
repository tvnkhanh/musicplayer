package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.AlbumDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumDetailsDAO {
    public List<AlbumDetails> getAllAlbumDetails() throws Exception {
        List<AlbumDetails> lstAlbumDetails = new ArrayList<>();
        String sql = "select * from Album_Details";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                AlbumDetails albumDetails = new AlbumDetails(rs.getInt("ALBUM_ID"), rs.getInt("GENRE_ID"));

                lstAlbumDetails.add(albumDetails);
            }
            return lstAlbumDetails;
        }
    }

    public boolean insertAlbumDetails(AlbumDetails albumDetails) throws Exception {
        String sql = "insert into Album_Details(ALBUM_ID, GENRE_ID) values (?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, albumDetails.getAlbumId());
            pstm.setInt(2, albumDetails.getGenreId());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateAlbumDetails(AlbumDetails albumDetails) throws Exception {
        String sql = "Update Album_Details set ALBUM_ID=?, GENRE_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, albumDetails.getAlbumId());
            pstm.setInt(2, albumDetails.getGenreId());

            return pstm.executeUpdate() > 0;
        }

    }

    public AlbumDetails findAlbumDetails(int albumId, int genreId) throws Exception {
        String sql = "select * from Album_Details where ALBUM_ID= ? and GENRE_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, albumId);
            pstm.setInt(2, genreId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                AlbumDetails albumDetails = new AlbumDetails(rs.getInt("ALBUM_ID"), rs.getInt("GENRE_ID"));

                return albumDetails;
            }
            return null;
        }

    }

    public boolean deleteAlbumDetails(int albumId, int genreId) throws Exception {
        String sql = "delete from Album_Details where ALBUM_ID= ? and GENRE_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, albumId);
            pstm.setInt(2, genreId);
            return pstm.executeUpdate() > 0;
        }

    }
}
