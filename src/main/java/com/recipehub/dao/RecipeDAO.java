package com.recipehub.dao;

import com.recipehub.model.Difficulty;
import com.recipehub.model.Recipe;
import com.recipehub.model.RecipeStatus;
import com.recipehub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeDAO implements CrudDAO<Recipe> {
    @Override
    public Optional<Recipe> findById(int id) throws SQLException {
        String sql = "SELECT r.*, u.name AS author_name, COALESCE(AVG(rv.rating),0) AS avg_rating " +
                     "FROM recipes r JOIN users u ON u.id=r.author_id " +
                     "LEFT JOIN reviews rv ON rv.recipe_id=r.id WHERE r.id=? GROUP BY r.id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }
        }
    }

    public List<Recipe> findApproved(String search, String cuisine) throws SQLException {
        String sql = "SELECT r.*, u.name AS author_name, COALESCE(AVG(rv.rating),0) AS avg_rating " +
                "FROM recipes r JOIN users u ON u.id=r.author_id " +
                "LEFT JOIN reviews rv ON rv.recipe_id=r.id " +
                "WHERE r.status='APPROVED' AND (?='' OR r.title LIKE ? OR r.description LIKE ? OR r.cuisine LIKE ?) " +
                "AND (?='' OR r.cuisine=?) GROUP BY r.id ORDER BY r.created_at DESC";
        List<Recipe> recipes = new ArrayList<>();
        String value = search == null ? "" : search.trim();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + value + "%";
            ps.setString(1, value);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            String selectedCuisine = cuisine == null ? "" : cuisine.trim();
            ps.setString(5, selectedCuisine);
            ps.setString(6, selectedCuisine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) recipes.add(mapRow(rs));
            }
        }
        return recipes;
    }

    public List<Recipe> findByAuthor(int authorId) throws SQLException {
        String sql = "SELECT r.*, u.name AS author_name, COALESCE(AVG(rv.rating),0) AS avg_rating " +
                "FROM recipes r JOIN users u ON u.id=r.author_id " +
                "LEFT JOIN reviews rv ON rv.recipe_id=r.id " +
                "WHERE r.author_id=? GROUP BY r.id ORDER BY r.created_at DESC";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) recipes.add(mapRow(rs));
            }
        }
        return recipes;
    }

    public List<Recipe> findForModeration() throws SQLException {
        String sql = "SELECT r.*, u.name AS author_name, COALESCE(AVG(rv.rating),0) AS avg_rating " +
                "FROM recipes r JOIN users u ON u.id=r.author_id LEFT JOIN reviews rv ON rv.recipe_id=r.id " +
                "GROUP BY r.id ORDER BY CASE r.status WHEN 'PENDING' THEN 0 ELSE 1 END, r.created_at DESC";
        List<Recipe> recipes = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) recipes.add(mapRow(rs));
        }
        return recipes;
    }

    public boolean updateStatus(int recipeId, RecipeStatus status) throws SQLException {
        String sql = "UPDATE recipes SET status=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, recipeId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Recipe> findAll() throws SQLException {
        return findApproved("", "");
    }

    @Override
    public int insert(Recipe recipe) throws SQLException {
        String sql = "INSERT INTO recipes (author_id,title,description,prep_minutes,cook_minutes,servings,difficulty,cuisine,image_url,status) " +
                     "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, recipe.getAuthorId());
            ps.setString(2, recipe.getTitle());
            ps.setString(3, recipe.getDescription());
            ps.setInt(4, recipe.getPrepMinutes());
            ps.setInt(5, recipe.getCookMinutes());
            ps.setInt(6, recipe.getServings());
            ps.setString(7, recipe.getDifficulty().name());
            ps.setString(8, recipe.getCuisine());
            ps.setString(9, recipe.getImageUrl());
            ps.setString(10, recipe.getStatus().name());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    /**
     * Inserts a recipe using an existing database connection.
     *
     * This method is intended for multi-table transactions where
     * the recipe, ingredients and instructions must be committed
     * together.
     */
    public int insert(
            Connection connection,
            Recipe recipe
    ) throws SQLException {

        String sql = """
            INSERT INTO recipes
                (
                    author_id,
                    title,
                    description,
                    prep_minutes,
                    cook_minutes,
                    servings,
                    difficulty,
                    cuisine,
                    image_url,
                    status
                )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            ps.setInt(1, recipe.getAuthorId());
            ps.setString(2, recipe.getTitle());
            ps.setString(3, recipe.getDescription());
            ps.setInt(4, recipe.getPrepMinutes());
            ps.setInt(5, recipe.getCookMinutes());
            ps.setInt(6, recipe.getServings());
            ps.setString(7, recipe.getDifficulty().name());
            ps.setString(8, recipe.getCuisine());
            ps.setString(9, recipe.getImageUrl());
            ps.setString(10, recipe.getStatus().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return 0;
    }

    @Override
    public boolean update(Recipe recipe) throws SQLException {
        String sql = "UPDATE recipes SET title=?,description=?,prep_minutes=?,cook_minutes=?,servings=?,difficulty=?,cuisine=?,image_url=? WHERE id=? AND author_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, recipe.getTitle());
            ps.setString(2, recipe.getDescription());
            ps.setInt(3, recipe.getPrepMinutes());
            ps.setInt(4, recipe.getCookMinutes());
            ps.setInt(5, recipe.getServings());
            ps.setString(6, recipe.getDifficulty().name());
            ps.setString(7, recipe.getCuisine());
            ps.setString(8, recipe.getImageUrl());
            ps.setInt(9, recipe.getId());
            ps.setInt(10, recipe.getAuthorId());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM recipes WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Recipe mapRow(ResultSet rs) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(rs.getInt("id"));
        recipe.setAuthorId(rs.getInt("author_id"));
        recipe.setAuthorName(rs.getString("author_name"));
        recipe.setTitle(rs.getString("title"));
        recipe.setDescription(rs.getString("description"));
        recipe.setPrepMinutes(rs.getInt("prep_minutes"));
        recipe.setCookMinutes(rs.getInt("cook_minutes"));
        recipe.setServings(rs.getInt("servings"));
        recipe.setDifficulty(Difficulty.valueOf(rs.getString("difficulty")));
        recipe.setCuisine(rs.getString("cuisine"));
        recipe.setImageUrl(rs.getString("image_url"));
        recipe.setStatus(RecipeStatus.valueOf(rs.getString("status")));
        recipe.setViews(rs.getInt("views"));
        recipe.setAverageRating(rs.getDouble("avg_rating"));
        if (rs.getTimestamp("created_at") != null) {
            recipe.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return recipe;
    }
}
