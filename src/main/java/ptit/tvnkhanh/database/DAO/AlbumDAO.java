package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {
    public List<Album> getAllAlbum() throws Exception {
        List<Album> lstAlbum = new ArrayList<>();
        String sql = "select * from Album";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Album album = new Album(rs.getInt("ALBUM_ID"), rs.getNString("NAME"), rs.getString("IMG_URI"), rs.getDate("RELEASE_DATE"),
                        rs.getString("SINGLE"), rs.getInt("ARTIST_ID"));
                lstAlbum.add(album);
            }
            return lstAlbum;
        }
    }

    public boolean insertAlbum(Album album) throws Exception {
        String sql = "insert into Album(ALBUM_ID, NAME, IMG_URI, RELEASE_DATE,SINGLE, ARTIST_ID) values (?,?,?,?,?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, album.getAlbumId());
            pstm.setNString(2, album.getName());
            pstm.setString(3, album.getImgURI());
            pstm.setDate(4, album.getReleaseDate());
            pstm.setString(5, album.getSingle());
            pstm.setInt(6, album.getArtistId());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateAlbum(Album album) throws Exception {
        String sql = "Update Album set NAME=?, IMG_URI=?, RELEASE_DATE=?,SINGLE=?, ARTIST_ID=? where ALBUM_ID = ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setNString(1, album.getName());
            pstm.setString(2, album.getImgURI());
            pstm.setDate(3, album.getReleaseDate());
            pstm.setString(4, album.getSingle());
            pstm.setInt(5, album.getArtistId());
            pstm.setInt(6, album.getAlbumId());

            return pstm.executeUpdate() > 0;
        }

    }

    public Album findAlbum(int id) throws Exception {
        String sql = "select * from Album where ALBUM_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Album album = new Album(rs.getInt("ALBUM_ID"), rs.getNString("NAME"), rs.getString("IMG_URI"),
                        rs.getDate("RELEASE_DATE"), rs.getString("SINGLE"), rs.getInt("ARTIST_ID"));

                return album;
            }
            return null;
        }

    }

    public Album findAlbum(String title) throws Exception {
        String sql = "select * from Album where NAME= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, title);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Album album = new Album(rs.getInt("ALBUM_ID"), rs.getNString("NAME"), rs.getString("IMG_URI"),
                        rs.getDate("RELEASE_DATE"), rs.getString("SINGLE"), rs.getInt("ARTIST_ID"));

                return album;
            }
            return null;
        }

    }

    public boolean deleteAlbum(int id) throws Exception {
        String sql = "delete from Album where ALBUM_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        }

    }
}
