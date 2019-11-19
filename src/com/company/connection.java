package com.company;

import java.sql.*;

public class connection {
    /**
     * Se connecter à la base de données MySQL
     *
     * @throws SQLException
     */
    public static Connection getConexionMYSQL() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetbanque2?serverTimezone=UTC", "root", "");

        return conn;
    }
}
