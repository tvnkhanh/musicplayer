package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO {
    public List<Artist> getAllArtist() throws Exception {
        List<Artist> lstArtist = new ArrayList<Artist>();
        String sql = "select * from ARTIST";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Artist artist = new Artist(rs.getInt("ARTIST_ID"), rs.getString("NAME"), rs.getString("COUNTRY"));
                lstArtist.add(artist);
            }
            return lstArtist;
        }
    }

    public boolean insertArtist(Artist artist) throws Exception {
        String sql = "insert into Artist(ARTIST_ID, NAME, COUNTRY) values (?,?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, artist.getArtistId());
            pstm.setString(2, artist.getName());
            pstm.setString(3, artist.getCountry());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateArtist(Artist artist) throws Exception {
        String sql = "Update Artist set NAME = ? ,COUNTRY =? where ARTIST_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, artist.getName());
            pstm.setString(2, artist.getCountry());
            pstm.setInt(3, artist.getArtistId());

            return pstm.executeUpdate() > 0;
        }

    }

    public Artist findArtist(String name) throws Exception {
        String sql = "select * from Artist where NAME= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, name);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Artist artist = new Artist(rs.getInt("ARTIST_ID"), rs.getString("NAME"), rs.getString("COUNTRY"));

                return artist;
            }
            return null;
        }
    }

    public Artist findArtist(int id) throws Exception {
        String sql = "select * from Artist where ARTIST_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Artist artist = new Artist(rs.getInt("ARTIST_ID"), rs.getString("NAME"), rs.getString("COUNTRY"));

                return artist;
            }
            return null;
        }
    }

    public boolean deleteArtist(int artistId) throws Exception {
        String sql = "delete from Artist where ARTIST_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, artistId);
            return pstm.executeUpdate() > 0;
        }

    }
}
