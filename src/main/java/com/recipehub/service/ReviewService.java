package com.recipehub.service;

import com.recipehub.dao.ReviewDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.Review;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewService {

    private final ReviewDAO reviewDAO;

    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    public List<Review> getReviewsForRecipe(int recipeId) {
        if (recipeId <= 0) {
            throw new ValidationException("Invalid recipe.");
        }

        try {
            return reviewDAO.findByRecipeId(recipeId);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Reviews could not be loaded.",
                    e
            );
        }
    }

    public Optional<Review> getUserReview(int recipeId, int userId) {
        if (recipeId <= 0 || userId <= 0) {
            throw new ValidationException("Invalid review information.");
        }

        try {
            return reviewDAO.findByRecipeAndUser(recipeId, userId);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Your review could not be loaded.",
                    e
            );
        }
    }

    public void saveReview(
            int recipeId,
            int userId,
            int rating,
            String reviewText
    ) {
        if (recipeId <= 0) {
            throw new ValidationException("Invalid recipe.");
        }

        if (userId <= 0) {
            throw new ValidationException("You must be logged in to review a recipe.");
        }

        if (rating < 1 || rating > 5) {
            throw new ValidationException("Rating must be between 1 and 5.");
        }

        String cleanedText = reviewText == null
                ? ""
                : reviewText.trim();

        if (cleanedText.length() > 1000) {
            throw new ValidationException(
                    "Review cannot be longer than 1000 characters."
            );
        }

        try {
            Optional<Review> existingReview =
                    reviewDAO.findByRecipeAndUser(recipeId, userId);

            if (existingReview.isPresent()) {
                Review review = existingReview.get();
                review.setRating(rating);
                review.setReviewText(cleanedText);

                reviewDAO.update(review);
            } else {
                Review review = new Review();
                review.setRecipeId(recipeId);
                review.setUserId(userId);
                review.setRating(rating);
                review.setReviewText(cleanedText);

                reviewDAO.insert(review);
            }

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Review could not be saved.",
                    e
            );
        }
    }
}