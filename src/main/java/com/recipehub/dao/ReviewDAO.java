package com.recipehub.dao;

import com.recipehub.model.Review;
import com.recipehub.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDAO {

    public List<Review> findByRecipeId(int recipeId) throws SQLException {

        String sql = """
                SELECT r.*, u.name AS user_name
                FROM reviews r
                JOIN users u ON u.id = r.user_id
                WHERE r.recipe_id = ?
                ORDER BY r.created_at DESC
                """;

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, recipeId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    reviews.add(mapRow(rs));
                }
            }
        }

        return reviews;
    }

    public Optional<Review> findByRecipeAndUser(int recipeId, int userId)
            throws SQLException {

        String sql = """
                SELECT r.*, u.name AS user_name
                FROM reviews r
                JOIN users u ON u.id = r.user_id
                WHERE r.recipe_id = ? AND r.user_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, recipeId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        }

        return Optional.empty();
    }

    public int insert(Review review) throws SQLException {

        String sql = """
                INSERT INTO reviews
                    (recipe_id, user_id, rating, review_text)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, review.getRecipeId());
            ps.setInt(2, review.getUserId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getReviewText());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public boolean update(Review review) throws SQLException {

        String sql = """
                UPDATE reviews
                SET rating = ?, review_text = ?
                WHERE id = ? AND user_id = ?
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getReviewText());
            ps.setInt(3, review.getId());
            ps.setInt(4, review.getUserId());

            return ps.executeUpdate() > 0;
        }
    }

    public int countAll() throws SQLException {

        String sql = "SELECT COUNT(*) FROM reviews";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    private Review mapRow(ResultSet rs) throws SQLException {

        Review review = new Review();

        review.setId(rs.getInt("id"));
        review.setRecipeId(rs.getInt("recipe_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setUserName(rs.getString("user_name"));
        review.setRating(rs.getInt("rating"));
        review.setReviewText(rs.getString("review_text"));

        if (rs.getTimestamp("created_at") != null) {
            review.setCreatedAt(
                    rs.getTimestamp("created_at").toLocalDateTime()
            );
        }

        return review;
    }
}