package com.recipehub.service;

import com.recipehub.dao.IngredientDAO;
import com.recipehub.dao.InstructionDAO;
import com.recipehub.dao.RecipeDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.Ingredient;
import com.recipehub.model.Recipe;
import com.recipehub.model.RecipeStatus;
import com.recipehub.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RecipeService {
    private final RecipeDAO recipeDAO;
    private final IngredientDAO ingredientDAO;
    private final InstructionDAO instructionDAO;

    public RecipeService(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
        this.ingredientDAO = new IngredientDAO();
        this.instructionDAO = new InstructionDAO();
    }

    public List<Recipe> getFeed(String search, String cuisine) {
        try {
            return recipeDAO.findApproved(search, cuisine);
        } catch (SQLException e) {
            throw new DataAccessException("Recipe feed could not be loaded.", e);
        }
    }

    public Optional<Recipe> getRecipe(int id) {
        try {
            return recipeDAO.findById(id);
        } catch (SQLException e) {
            throw new DataAccessException("Recipe could not be loaded.", e);
        }
    }

    public List<Ingredient> getIngredients(int recipeId) {
        try {
            return ingredientDAO.findByRecipeId(recipeId);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Recipe ingredients could not be loaded.",
                    e
            );
        }
    }

    public List<String> getInstructions(int recipeId) {
        try {
            return instructionDAO.findByRecipeId(recipeId);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Recipe instructions could not be loaded.",
                    e
            );
        }
    }

    public List<Recipe> getMyRecipes(int authorId) {
        try {
            return recipeDAO.findByAuthor(authorId);
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Contributor recipes could not be loaded.",
                    e
            );
        }
    }

    public void updateRecipeStatus(int recipeId, String status) {

        if (status == null || status.isBlank()) {
            throw new ValidationException(
                    "Recipe status is required."
            );
        }

        try {
            RecipeStatus recipeStatus =
                    RecipeStatus.valueOf(
                            status.toUpperCase()
                    );

            if (recipeStatus != RecipeStatus.APPROVED
                    && recipeStatus != RecipeStatus.REJECTED) {

                throw new ValidationException(
                        "Only APPROVED or REJECTED status is allowed."
                );
            }

            boolean updated =
                    recipeDAO.updateStatus(
                            recipeId,
                            recipeStatus
                    );

            if (!updated) {
                throw new ValidationException(
                        "Recipe could not be found."
                );
            }

        } catch (IllegalArgumentException e) {
            throw new ValidationException(
                    "Invalid recipe status."
            );
        } catch (SQLException e) {
            throw new DataAccessException(
                    "Recipe status could not be updated.",
                    e
            );
        }
    }

    public int createRecipe(Recipe recipe) {
        if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            throw new ValidationException("Recipe title is required.");
        }
        if (recipe.getDescription() == null || recipe.getDescription().isBlank()) {
            throw new ValidationException("Recipe description is required.");
        }
        try {
            return recipeDAO.insert(recipe);
        } catch (SQLException e) {
            throw new DataAccessException("Recipe could not be saved.", e);
        }
    }

    /**
     * Creates a complete recipe together with its ingredients
     * and instructions inside one database transaction.
     */
    public int createRecipeWithDetails(
            Recipe recipe,
            List<Ingredient> ingredients,
            List<String> instructions
    ) {

        validateRecipe(recipe);

        if (ingredients == null || ingredients.isEmpty()) {
            throw new ValidationException(
                    "At least one ingredient is required."
            );
        }

        if (instructions == null || instructions.isEmpty()) {
            throw new ValidationException(
                    "At least one instruction is required."
            );
        }

        try (Connection connection =
                     DBConnection.getConnection()) {

            try {

                connection.setAutoCommit(false);

                /*
                 * Insert the main recipe first.
                 */
                int recipeId =
                        recipeDAO.insert(connection, recipe);

                if (recipeId <= 0) {
                    throw new SQLException(
                            "Recipe ID was not generated."
                    );
                }

                /*
                 * Insert ingredients belonging to the
                 * newly created recipe.
                 */
                for (int i = 0; i < ingredients.size(); i++) {

                    Ingredient ingredient =
                            ingredients.get(i);

                    ingredient.setRecipeId(recipeId);
                    ingredient.setSortOrder(i + 1);
                }

                ingredientDAO.insertAll(
                        connection,
                        recipeId,
                        ingredients
                );

                /*
                 * Insert cooking instructions.
                 */
                instructionDAO.insertAll(
                        connection,
                        recipeId,
                        instructions
                );

                /*
                 * Everything succeeded.
                 */
                connection.commit();

                return recipeId;

            } catch (Exception e) {

                /*
                 * Something failed.
                 * Undo every database operation performed
                 * during this transaction.
                 */
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    e.addSuppressed(rollbackException);
                }

                if (e instanceof ValidationException validationException) {
                    throw validationException;
                }

                if (e instanceof SQLException sqlException) {
                    throw new DataAccessException(
                            "Recipe could not be submitted.",
                            sqlException
                    );
                }

                throw new DataAccessException(
                        "Recipe could not be submitted.",
                        e
                );

            } finally {

                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ignored) {
                    // Connection is closing anyway.
                }
            }

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Database connection could not be established.",
                    e
            );
        }
    }


    /**
     * Validates the main recipe information before
     * starting the database transaction.
     */
    private void validateRecipe(Recipe recipe) {

        if (recipe == null) {
            throw new ValidationException(
                    "Recipe information is required."
            );
        }

        if (recipe.getTitle() == null
                || recipe.getTitle().isBlank()) {

            throw new ValidationException(
                    "Recipe title is required."
            );
        }

        if (recipe.getDescription() == null
                || recipe.getDescription().isBlank()) {

            throw new ValidationException(
                    "Recipe description is required."
            );
        }

        if (recipe.getPrepMinutes() < 0) {
            throw new ValidationException(
                    "Preparation time cannot be negative."
            );
        }

        if (recipe.getCookMinutes() < 0) {
            throw new ValidationException(
                    "Cooking time cannot be negative."
            );
        }

        if (recipe.getServings() <= 0) {
            throw new ValidationException(
                    "Servings must be greater than zero."
            );
        }

        if (recipe.getDifficulty() == null) {
            throw new ValidationException(
                    "Recipe difficulty is required."
            );
        }

        if (recipe.getCuisine() == null
                || recipe.getCuisine().isBlank()) {

            throw new ValidationException(
                    "Cuisine is required."
            );
        }
    }
}
