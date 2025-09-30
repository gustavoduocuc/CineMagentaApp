package com.cinemagenta.controller;

import com.cinemagenta.model.DatabaseConnection;
import com.cinemagenta.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class MovieController {

    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO movie_catalog (title, director, release_year, duration_minutes, genre) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDirector());
            stmt.setInt(3, movie.getReleaseYear());
            stmt.setInt(4, movie.getDurationMinutes());
            stmt.setString(5, movie.getGenre());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar película en la base de datos:");
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateMovie(Movie movie) {
        String sql = "UPDATE movie_catalog SET title=?, director=?, release_year=?, duration_minutes=?, genre=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getDirector());
            stmt.setInt(3, movie.getReleaseYear());
            stmt.setInt(4, movie.getDurationMinutes());
            stmt.setString(5, movie.getGenre());
            stmt.setInt(6, movie.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar película:");
            e.printStackTrace();
            return false;
        }
    }
    
    
    public List<Movie> findMovies(String searchTerm) {
        List<Movie> movies = new ArrayList<>();
        String sql = """
            SELECT * FROM movie_catalog 
            WHERE title LIKE ? OR director LIKE ? OR CAST(release_year AS CHAR) LIKE ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String wildcard = "%" + searchTerm + "%";
            stmt.setString(1, wildcard);
            stmt.setString(2, wildcard);
            stmt.setString(3, wildcard);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("release_year"),
                        rs.getInt("duration_minutes"),
                        rs.getString("genre")
                );
                movies.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }
    
    public Movie findMovieById(int id) {
        String sql = "SELECT * FROM movie_catalog WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("release_year"),
                        rs.getInt("duration_minutes"),
                        rs.getString("genre")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public boolean deleteMovieById(int id) {
        String sql = "DELETE FROM movie_catalog WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}