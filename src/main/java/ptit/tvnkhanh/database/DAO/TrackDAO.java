package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {
    public List<Track> getAllTrack() throws Exception {
        List<Track> lstTrack = new ArrayList<>();
        String sql = "select * from Track";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Track track = new Track(rs.getInt("TRACK_ID"), rs.getNString("TITLE"), rs.getString("FILE_URI"),
                        rs.getInt("NUM_VIEW"), rs.getInt("ALBUM_ID"));

                lstTrack.add(track);
            }
            return lstTrack;
        }
    }

    public boolean insertTrack(Track track) throws Exception {
        String sql = "insert into Track(TRACK_ID, TITLE, FILE_URI, NUM_VIEW, ALBUM_ID) values (?,?,?,?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, track.getTrackId());
            pstm.setNString(2, track.getTitle());
            pstm.setString(3, track.getFileURI());
            pstm.setInt(4, track.getNumView());
            pstm.setInt(5, track.getAlbumId());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateTrack(Track track) throws Exception {
        String sql = "Update Track set TITLE=?, FILE_URI=?, NUM_VIEW=?, ALBUM_ID=? where TRACK_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setNString(1, track.getTitle());
            pstm.setString(2, track.getFileURI());
            pstm.setInt(3, track.getNumView());
            pstm.setInt(4, track.getAlbumId());
            pstm.setInt(5, track.getTrackId());

            return pstm.executeUpdate() > 0;
        }

    }

    public Track findTrack(int id) throws Exception {
        String sql = "select * from Track where TRACK_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Track track = new Track(rs.getInt("TRACK_ID"), rs.getString("TITLE"), rs.getString("FILE_URI"),
                        rs.getInt("NUM_VIEW"), rs.getInt("ALBUM_ID"));

                return track;
            }
            return null;
        }

    }

    public Track findTrack(String title) throws Exception {
        String sql = "select * from Track where TITLE= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, title);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Track track = new Track(rs.getInt("TRACK_ID"), rs.getString("TITLE"), rs.getString("FILE_URI"),
                        rs.getInt("NUM_VIEW"), rs.getInt("ALBUM_ID"));

                return track;
            }
            return null;
        }

    }

    public boolean deleteTrack(int id) throws Exception {
        String sql = "delete from Track where TRACK_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        }

    }
}
