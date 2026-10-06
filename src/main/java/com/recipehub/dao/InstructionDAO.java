package com.recipehub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InstructionDAO {

    /**
     * Inserts all instructions belonging to a recipe.
     *
     * The supplied connection allows these inserts to participate
     * in the same transaction as the recipe and ingredients.
     */
    public void insertAll(
            Connection connection,
            int recipeId,
            List<String> instructions
    ) throws SQLException {

        String sql = """
                INSERT INTO instructions
                    (recipe_id, step_no, instruction_text)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            for (int i = 0; i < instructions.size(); i++) {

                ps.setInt(1, recipeId);
                ps.setInt(2, i + 1);
                ps.setString(3, instructions.get(i));

                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    public List<String> findByRecipeId(int recipeId) throws SQLException {

        String sql = """
            SELECT instruction_text
            FROM instructions
            WHERE recipe_id = ?
            ORDER BY step_no ASC
            """;

        List<String> instructions = new java.util.ArrayList<>();

        try (Connection connection = com.recipehub.util.DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, recipeId);

            try (var rs = ps.executeQuery()) {

                while (rs.next()) {
                    instructions.add(
                            rs.getString("instruction_text")
                    );
                }
            }
        }

        return instructions;
    }
}