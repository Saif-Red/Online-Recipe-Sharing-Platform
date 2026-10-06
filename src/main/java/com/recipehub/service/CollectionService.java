package com.recipehub.service;

import com.recipehub.dao.CollectionDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.Collection;
import com.recipehub.model.Recipe;

import java.sql.SQLException;
import java.util.List;

public class CollectionService {

    private final CollectionDAO collectionDAO;

    public CollectionService(CollectionDAO collectionDAO) {
        this.collectionDAO = collectionDAO;
    }

    /**
     * Returns all collections belonging to a user.
     */
    public List<Collection> getUserCollections(int userId) {

        if (userId <= 0) {
            throw new ValidationException("Invalid user.");
        }

        try {
            return collectionDAO.findByUserId(userId);

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Collections could not be loaded.",
                    e
            );
        }
    }


    /**
     * Creates a new collection.
     */
    public int createCollection(
            int userId,
            String name,
            String description
    ) {

        if (userId <= 0) {
            throw new ValidationException("Invalid user.");
        }

        String cleanedName = name == null
                ? ""
                : name.trim();

        String cleanedDescription = description == null
                ? ""
                : description.trim();

        if (cleanedName.isBlank()) {
            throw new ValidationException(
                    "Collection name is required."
            );
        }

        if (cleanedName.length() > 100) {
            throw new ValidationException(
                    "Collection name cannot be longer than 100 characters."
            );
        }

        if (cleanedDescription.length() > 300) {
            throw new ValidationException(
                    "Collection description cannot be longer than 300 characters."
            );
        }

        Collection collection = new Collection();

        collection.setUserId(userId);
        collection.setName(cleanedName);
        collection.setDescription(cleanedDescription);

        try {
            return collectionDAO.insert(collection);

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Collection could not be created.",
                    e
            );
        }
    }


    /**
     * Checks whether a recipe is already saved
     * in a particular collection.
     */
    public boolean containsRecipe(
            int collectionId,
            int recipeId
    ) {

        if (collectionId <= 0 || recipeId <= 0) {
            throw new ValidationException(
                    "Invalid collection or recipe."
            );
        }

        try {
            return collectionDAO.containsRecipe(
                    collectionId,
                    recipeId
            );

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Could not check saved recipe.",
                    e
            );
        }
    }


    /**
     * Saves a recipe to a collection.
     */
    public boolean saveRecipe(
            int collectionId,
            int recipeId
    ) {

        if (collectionId <= 0 || recipeId <= 0) {
            throw new ValidationException(
                    "Invalid collection or recipe."
            );
        }

        try {

            if (collectionDAO.containsRecipe(
                    collectionId,
                    recipeId
            )) {
                return false;
            }

            return collectionDAO.addRecipe(
                    collectionId,
                    recipeId
            );

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Recipe could not be saved.",
                    e
            );
        }
    }


    /**
     * Removes a recipe from a collection.
     */
    public boolean removeRecipe(
            int collectionId,
            int recipeId
    ) {

        if (collectionId <= 0 || recipeId <= 0) {
            throw new ValidationException(
                    "Invalid collection or recipe."
            );
        }

        try {
            return collectionDAO.removeRecipe(
                    collectionId,
                    recipeId
            );

        } catch (SQLException e) {
            throw new DataAccessException(
                    "Recipe could not be removed from the collection.",
                    e
            );
        }
    }

    /**
     * Deletes a collection owned by the specified user.
     */
    public boolean deleteCollection(
            int collectionId,
            int userId
    ) {

        if (collectionId <= 0 || userId <= 0) {
            throw new ValidationException(
                    "Invalid collection."
            );
        }

        try {

            boolean deleted =
                    collectionDAO.delete(
                            collectionId,
                            userId
                    );

            if (!deleted) {
                throw new ValidationException(
                        "Collection could not be deleted."
                );
            }

            return true;

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Collection could not be deleted.",
                    e
            );
        }
    }
    /**
     * Returns all recipes saved inside a collection.
     */
    public List<Recipe> getRecipesInCollection(
            int collectionId
    ) {

        if (collectionId <= 0) {
            throw new ValidationException(
                    "Invalid collection."
            );
        }

        try {

            return collectionDAO.findRecipesByCollectionId(
                    collectionId
            );

        } catch (SQLException e) {

            throw new DataAccessException(
                    "Saved recipes could not be loaded.",
                    e
            );
        }
    }
}