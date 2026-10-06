package com.recipehub.dao;

import com.recipehub.model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class IngredientDAO {

    /**
     * Inserts all ingredients belonging to a recipe.
     *
     * This method uses the connection supplied by the caller so that
     * the operation can participate in the same JDBC transaction
     * as the recipe and instructions.
     */
    public void insertAll(
            Connection connection,
            int recipeId,
            List<Ingredient> ingredients
    ) throws SQLException {

        String sql = """
                INSERT INTO ingredients
                    (recipe_id, item_name, quantity, sort_order)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            for (Ingredient ingredient : ingredients) {

                ps.setInt(1, recipeId);
                ps.setString(2, ingredient.getItemName());
                ps.setString(3, ingredient.getQuantity());
                ps.setInt(4, ingredient.getSortOrder());

                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    public List<Ingredient> findByRecipeId(int recipeId) throws SQLException {

        String sql = """
            SELECT id, recipe_id, item_name, quantity, sort_order
            FROM ingredients
            WHERE recipe_id = ?
            ORDER BY sort_order ASC
            """;

        List<Ingredient> ingredients = new java.util.ArrayList<>();

        try (Connection connection = com.recipehub.util.DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, recipeId);

            try (var rs = ps.executeQuery()) {

                while (rs.next()) {

                    Ingredient ingredient = new Ingredient();

                    ingredient.setId(rs.getInt("id"));
                    ingredient.setRecipeId(rs.getInt("recipe_id"));
                    ingredient.setItemName(rs.getString("item_name"));
                    ingredient.setQuantity(rs.getString("quantity"));
                    ingredient.setSortOrder(rs.getInt("sort_order"));

                    ingredients.add(ingredient);
                }
            }
        }

        return ingredients;
    }
}