package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.PlaylistDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PlaylistDetailsDAO {
    public boolean insertPlaylistDetails(PlaylistDetails playlistDetails) throws Exception {
        String sql = "insert into Playlist_Details(PLAYLIST_ID, TRACK_ID) values (?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, playlistDetails.getPlaylistId());
            pstm.setInt(2, playlistDetails.getTrackId());

            return pstm.executeUpdate() > 0;
        }

    }
}
