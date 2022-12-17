package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.Artist;
import ptit.tvnkhanh.database.model.Playlist;
import ptit.tvnkhanh.database.model.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDAO {
    public List<Playlist> getAllPlaylist() throws Exception {
        List<Playlist> lstPlaylist = new ArrayList<>();
        String sql = "select * from Playlist";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Playlist playlist = new Playlist(rs.getNString("TITLE"), rs.getString("IMAGE"), rs.getInt("USER_ID"));

                lstPlaylist.add(playlist);
            }
            return lstPlaylist;
        }
    }

    public boolean insertPlaylist(Playlist playlist) throws Exception {
        String sql = "insert into Playlist(TITLE, IMAGE, USER_ID) values (?,?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setNString(1, playlist.getTitle());
            pstm.setString(2, playlist.getImageURI());
            pstm.setInt(3, playlist.getUserId());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updatePlaylist(Playlist playlist) throws Exception {
        String sql = "Update Playlist set TITLE=?, IMAGE=? where USER_ID=?";// !!!!!
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setNString(1, playlist.getTitle());
            pstm.setString(2, playlist.getImageURI());
            pstm.setInt(3, playlist.getUserId());

            return pstm.executeUpdate() > 0;
        }

    }

    public Playlist findPlaylist(int id) throws Exception {
        String sql = "select * from Playlist where USER_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Playlist playlist = new Playlist(rs.getNString("TITLE"), rs.getString("IMAGE"), rs.getInt("USER_ID"));

                return playlist;
            }
            return null;
        }

    }

    public Playlist findPlaylist(String title) throws Exception {
        String sql = "select * from Playlist where TITLE= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, title);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Playlist playlist = new Playlist(rs.getNString("TITLE"), rs.getString("IMAGE"), rs.getInt("USER_ID"));

                return playlist;
            }
            return null;
        }

    }

    public boolean deletePlaylist(int id) throws Exception {
        String sql = "delete from Playlist where PLAYLIST_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        }

    }

    public List<Artist> getArtistInfo(int playlistId) throws Exception {
        List<Artist> artists = new ArrayList<>();
        try {
            Connection con = DatabaseHelper.openConnection();
            CallableStatement stmt = con.prepareCall("{call SP_PLAYLIST_ARTIST_NAME(?)}");
            stmt.setInt(1, playlistId);
            Boolean hasResult = stmt.execute();
            if (hasResult) {
                ResultSet rs = stmt.getResultSet();

                while (rs.next()) {
                    Artist artist = new Artist(rs.getInt("ARTIST_ID"), rs.getNString("ARTIST"), rs.getNString("COUNTRY"));
                    artists.add(artist);
                }
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return artists;
    }

    public List<Integer> getPlaylistId() throws SQLException {
        List<Integer> playlistIds = new ArrayList<>();
        String sql = "SELECT PLAYLIST_ID FROM PLAYLIST ORDER BY PLAYLIST_ID";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            while (rs.next()) {
                playlistIds.add(rs.getInt("PLAYLIST_ID"));
            }
            return playlistIds;
        }
    }
}
