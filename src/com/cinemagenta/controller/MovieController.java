package com.cinemagenta.controller;

import com.cinemagenta.model.DatabaseConnection;
import com.cinemagenta.model.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}