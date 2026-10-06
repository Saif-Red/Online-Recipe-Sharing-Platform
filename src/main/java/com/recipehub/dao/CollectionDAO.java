package com.recipehub.dao;

import com.recipehub.model.Collection;
import com.recipehub.model.Recipe;
import com.recipehub.model.Difficulty;
import com.recipehub.model.RecipeStatus;
import com.recipehub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectionDAO {

    /**
     * Finds all collections belonging to a specific user.
     */
    public List<Collection> findByUserId(int userId) throws SQLException {

        String sql = """
                SELECT *
                FROM collections
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        List<Collection> collections = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    collections.add(mapRow(rs));
                }
            }
        }

        return collections;
    }


    /**
     * Creates a new collection for a user.
     */
    public int insert(Collection collection) throws SQLException {

        String sql = """
                INSERT INTO collections
                    (user_id, name, description)
                VALUES (?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            ps.setInt(1, collection.getUserId());
            ps.setString(2, collection.getName());
            ps.setString(3, collection.getDescription());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {

                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return 0;
    }


    /**
     * Checks whether a recipe is already saved
     * inside a particular collection.
     */
    public boolean containsRecipe(
            int collectionId,
            int recipeId
    ) throws SQLException {

        String sql = """
                SELECT 1
                FROM collection_items
                WHERE collection_id = ?
                  AND recipe_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, collectionId);
            ps.setInt(2, recipeId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    /**
     * Adds a recipe to a collection.
     */
    public boolean addRecipe(
            int collectionId,
            int recipeId
    ) throws SQLException {

        String sql = """
                INSERT INTO collection_items
                    (collection_id, recipe_id)
                VALUES (?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, collectionId);
            ps.setInt(2, recipeId);

            return ps.executeUpdate() > 0;
        }
    }


    /**
     * Removes a recipe from a collection.
     */
    public boolean removeRecipe(
            int collectionId,
            int recipeId
    ) throws SQLException {

        String sql = """
                DELETE FROM collection_items
                WHERE collection_id = ?
                  AND recipe_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, collectionId);
            ps.setInt(2, recipeId);

            return ps.executeUpdate() > 0;
        }
    }


    /**
     * Deletes a collection belonging to a specific user.
     *
     * The database ON DELETE CASCADE rule automatically
     * removes the collection's saved recipe entries.
     */
    public boolean delete(
            int collectionId,
            int userId
    ) throws SQLException {

        String sql = """
                DELETE FROM collections
                WHERE id = ?
                  AND user_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, collectionId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Finds all recipes saved inside a collection.
     */
    public List<Recipe> findRecipesByCollectionId(
            int collectionId
    ) throws SQLException {

        String sql = """
                SELECT
                    r.*,
                    u.name AS author_name,
                    COALESCE(
                        AVG(rv.rating),
                        0
                    ) AS avg_rating
                FROM collection_items ci
                JOIN recipes r
                    ON r.id = ci.recipe_id
                JOIN users u
                    ON u.id = r.author_id
                LEFT JOIN reviews rv
                    ON rv.recipe_id = r.id
                WHERE ci.collection_id = ?
                GROUP BY r.id
                ORDER BY ci.added_at DESC
                """;

        List<Recipe> recipes = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, collectionId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Recipe recipe = new Recipe();

                    recipe.setId(
                            rs.getInt("id")
                    );

                    recipe.setAuthorId(
                            rs.getInt("author_id")
                    );

                    recipe.setAuthorName(
                            rs.getString("author_name")
                    );

                    recipe.setTitle(
                            rs.getString("title")
                    );

                    recipe.setDescription(
                            rs.getString("description")
                    );

                    recipe.setPrepMinutes(
                            rs.getInt("prep_minutes")
                    );

                    recipe.setCookMinutes(
                            rs.getInt("cook_minutes")
                    );

                    recipe.setServings(
                            rs.getInt("servings")
                    );

                    recipe.setDifficulty(
                            Difficulty.valueOf(
                                    rs.getString("difficulty")
                            )
                    );

                    recipe.setCuisine(
                            rs.getString("cuisine")
                    );

                    recipe.setImageUrl(
                            rs.getString("image_url")
                    );

                    recipe.setStatus(
                            RecipeStatus.valueOf(
                                    rs.getString("status")
                            )
                    );

                    recipe.setViews(
                            rs.getInt("views")
                    );

                    recipe.setAverageRating(
                            rs.getDouble("avg_rating")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        recipe.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );
                    }

                    recipes.add(recipe);
                }
            }
        }

        return recipes;
    }

    /**
     * Converts a database row into a Collection object.
     */
    private Collection mapRow(ResultSet rs) throws SQLException {

        Collection collection = new Collection();

        collection.setId(rs.getInt("id"));
        collection.setUserId(rs.getInt("user_id"));
        collection.setName(rs.getString("name"));
        collection.setDescription(rs.getString("description"));

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            collection.setCreatedAt(
                    createdAt.toLocalDateTime()
            );
        }

        return collection;
    }
}