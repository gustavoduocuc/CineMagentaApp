package com.cinemagenta.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Cine_DB";
    private static final String USER = "PRY2203_EXP3";
    private static final String PASSWORD = "PRY2203";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}