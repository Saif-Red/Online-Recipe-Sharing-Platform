package com.recipehub.util;

import com.recipehub.exception.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/recipehub"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=Asia/Kolkata";

    private static final String USER = DBConfig.USER;
    private static final String PASSWORD = DBConfig.PASSWORD;

    private DBConnection() {
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (ClassNotFoundException e) {
            throw new DataAccessException(
                    "MySQL JDBC driver was not found.",
                    e
            );

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Could not connect to the recipehub database.",
                    e
            );
        }
    }
}