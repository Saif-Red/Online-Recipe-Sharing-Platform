package com.recipehub.controller;

import com.recipehub.dao.CollectionDAO;
import com.recipehub.model.Collection;
import com.recipehub.model.User;
import com.recipehub.service.CollectionService;
import com.recipehub.dao.RecipeDAO;
import com.recipehub.dao.ReviewDAO;
import com.recipehub.service.RecipeService;
import com.recipehub.service.ReviewService;
import com.recipehub.service.StatsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/recipe")
public class RecipeServlet extends BaseServlet {

    private final RecipeService recipeService =
            new RecipeService(new RecipeDAO());

    private final ReviewService reviewService =
            new ReviewService(new ReviewDAO());

    private final StatsService statsService =
            new StatsService();

    private final CollectionService collectionService =
            new CollectionService(new CollectionDAO());

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(
                    request.getParameter("id")
            );

            recipeService.getRecipe(id).ifPresent(recipe ->
                    request.setAttribute("recipe", recipe)
            );

            if (request.getAttribute("recipe") == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Recipe not found."
                );
                return;
            }

            request.setAttribute(
                    "reviews",
                    reviewService.getReviewsForRecipe(id)
            );

            request.setAttribute(
                    "ingredients",
                    recipeService.getIngredients(id)
            );

            request.setAttribute(
                    "instructions",
                    recipeService.getInstructions(id)
            );

            User user = currentUser(request);

            if (user != null) {

                List<Collection> collections =
                        collectionService.getUserCollections(user.getId());

                Set<Integer> savedCollectionIds =
                        new HashSet<>();

                for (Collection collection : collections) {

                    if (collectionService.containsRecipe(
                            collection.getId(),
                            id
                    )) {
                        savedCollectionIds.add(
                                collection.getId()
                        );
                    }
                }

                request.setAttribute(
                        "collections",
                        collections
                );

                request.setAttribute(
                        "savedCollectionIds",
                        savedCollectionIds
                );
            }

            statsService.registerView(id);

            request.getRequestDispatcher(
                    "/WEB-INF/views/recipe.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid recipe id."
            );
        }
    }
}