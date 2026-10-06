package com.recipehub.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginQueryTest {

    public static void main(String[] args) {

        String email = "admin@recipehub.local";

        String sql = "SELECT * FROM users WHERE email = ? AND active = TRUE";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("User found!");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Role: " + rs.getString("role"));
                    System.out.println("Active: " + rs.getBoolean("active"));
                    System.out.println("Password hash exists: "
                            + (rs.getString("password_hash") != null));
                    System.out.println("Avatar URL: " + rs.getString("avatar_url"));
                    System.out.println("Created at: " + rs.getTimestamp("created_at"));
                } else {
                    System.out.println("No user found.");
                }

            }

        } catch (Exception e) {
            System.err.println("Login query test failed:");
            e.printStackTrace();
        }
    }
}