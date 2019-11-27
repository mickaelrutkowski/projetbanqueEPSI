package com.company;

import java.sql.*;

public class connection {

    public static Connection getConexionMYSQL() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetbanque2?serverTimezone=UTC", "root", "");
        return conn;
    }
}
