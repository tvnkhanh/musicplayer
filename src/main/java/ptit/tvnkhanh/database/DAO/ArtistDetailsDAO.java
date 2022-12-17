package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.ArtistDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistDetailsDAO {
    public List<ArtistDetails> getAllArtistDetails() throws Exception {
        List<ArtistDetails> lstArtistDetails = new ArrayList<>();
        String sql = "select * from Artist_Details";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                ArtistDetails artistDetails = new ArtistDetails(rs.getInt("ARTIST_ID"), rs.getInt("ALBUM_ID"));

                lstArtistDetails.add(artistDetails);
            }
            return lstArtistDetails;
        }
    }

    public boolean insertArtistDetails(ArtistDetails artistDetails) throws Exception {
        String sql = "insert into Artist_Details(ARTIST_ID, ALBUM_ID) values (?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, artistDetails.getArtistId());
            pstm.setInt(2, artistDetails.getAlbumId());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateArtistDetails(ArtistDetails artistDetails) throws Exception {
        String sql = "Update Artist_Details set ALBUM_ID=? where ARTIST_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, artistDetails.getAlbumId());
            pstm.setInt(2, artistDetails.getArtistId());

            return pstm.executeUpdate() > 0;
        }

    }

    public ArtistDetails findArtistDetails(int artistId, int albumId) throws Exception {
        String sql = "select * from Artist_Details where ARTIST_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, artistId);
            pstm.setInt(2, albumId);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                ArtistDetails artistDetails = new ArtistDetails(rs.getInt("ARTIST_ID"), rs.getInt("ALBUM_ID"));

                return artistDetails;
            }
            return null;
        }

    }

    public boolean deleteArtistDetails(int artistId, int albumId) throws Exception {
        String sql = "delete from Artist_Details where ARTIST_ID= ? and ALBUM_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, artistId);
            pstm.setInt(2, albumId);
            return pstm.executeUpdate() > 0;
        }

    }
}
