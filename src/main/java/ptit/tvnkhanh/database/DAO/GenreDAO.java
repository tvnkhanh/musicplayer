package ptit.tvnkhanh.database.DAO;

import ptit.tvnkhanh.database.helper.DatabaseHelper;
import ptit.tvnkhanh.database.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {
    public List<Genre> getAllGenre() throws Exception {
        List<Genre> lstGenre = new ArrayList<>();
        String sql = "select * from Genre";
        try (
                Connection con = DatabaseHelper.openConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);) {

            while (rs.next()) {
                Genre genre = new Genre(rs.getInt("GENRE_ID"), rs.getNString("TITLE"));

                lstGenre.add(genre);
            }
            return lstGenre;
        }
    }

    public boolean insertGenre(Genre genre) throws Exception {
        String sql = "insert into Genre(GENRE_ID, TITLE) values (?,?)";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, genre.getGenreId());
            pstm.setNString(2, genre.getTitle());

            return pstm.executeUpdate() > 0;
        }

    }

    public boolean updateGenre(Genre genre) throws Exception {
        String sql = "Update Genre set TITLE=? where GENRE_ID=?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setNString(1, genre.getTitle());
            pstm.setInt(2, genre.getGenreId());

            return pstm.executeUpdate() > 0;
        }

    }

    public Genre findGenre(int id) throws Exception {
        String sql = "select * from Genre where GENRE_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Genre genre = new Genre(rs.getInt("GENRE_ID"), rs.getString("TITLE"));

                return genre;
            }
            return null;
        }

    }

    public Genre findGenre(String title) throws Exception {
        String sql = "select * from Genre where TITLE= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setString(1, title);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Genre genre = new Genre(rs.getInt("GENRE_ID"), rs.getString("TITLE"));

                return genre;
            }
            return null;
        }

    }

    public boolean deleteGenre(int id) throws Exception {
        String sql = "delete from Genre where GENRE_ID= ?";
        try (
                Connection con = DatabaseHelper.openConnection();
                PreparedStatement pstm = con.prepareStatement(sql);)
        {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        }

    }
}
