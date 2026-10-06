package com.recipehub.util;

import java.sql.Connection;

/** Run this class once before deploying to confirm JDBC configuration. */
public class ConnectionTest {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("JDBC connection successful: " + connection.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            System.err.println("JDBC connection failed:");
            e.printStackTrace();
        }
    }
}
