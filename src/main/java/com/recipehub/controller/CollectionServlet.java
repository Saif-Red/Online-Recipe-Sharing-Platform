package com.recipehub.controller;

import com.recipehub.dao.CollectionDAO;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.User;
import com.recipehub.service.CollectionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/collection")
public class CollectionServlet extends BaseServlet {

    private final CollectionService collectionService =
            new CollectionService(new CollectionDAO());


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        User user = currentUser(request);

        /*
         * Collections are available only to logged-in users.
         */
        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }


        String action = request.getParameter("action");


        try {

            if ("create".equals(action)) {

                createCollection(request, response, user);

            } else if ("save".equals(action)) {

                saveRecipe(request, response, user);

            } else if ("remove".equals(action)) {

                removeRecipe(request, response, user);

            } else if ("deleteCollection".equals(action)) {

                deleteCollection(request, response, user);

            } else {

                setFlash(
                        request,
                        "Invalid collection action."
                );

                response.sendRedirect(
                        request.getContextPath() + "/feed"
                );
            }

        } catch (NumberFormatException e) {

            setFlash(
                    request,
                    "Invalid collection or recipe."
            );

            response.sendRedirect(
                    request.getContextPath() + "/feed"
            );

        } catch (ValidationException e) {

            setFlash(
                    request,
                    e.getMessage()
            );

            String recipeId = request.getParameter("recipeId");

            if (recipeId != null && !recipeId.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/recipe?id=" + recipeId
                );

            } else {

                response.sendRedirect(
                        request.getContextPath() + "/feed"
                );
            }
        }
    }


    /**
     * Creates a new collection for the logged-in user.
     */
    private void createCollection(
            HttpServletRequest request,
            HttpServletResponse response,
            User user
    ) throws IOException {

        String name = request.getParameter("name");
        String description = request.getParameter("description");

        collectionService.createCollection(
                user.getId(),
                name,
                description
        );

        setFlash(
                request,
                "Collection created successfully."
        );

        response.sendRedirect(
                request.getContextPath() + "/collections"
        );
    }


    /**
     * Saves a recipe to the selected collection.
     */
    private void saveRecipe(
            HttpServletRequest request,
            HttpServletResponse response,
            User user
    ) throws IOException {

        int collectionId = Integer.parseInt(
                request.getParameter("collectionId")
        );

        int recipeId = Integer.parseInt(
                request.getParameter("recipeId")
        );

        /*
         * Verify that the selected collection belongs
         * to the currently logged-in user before modifying it.
         */
        if (!isUserCollection(user.getId(), collectionId)) {

            setFlash(
                    request,
                    "You cannot modify this collection."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/recipe?id=" + recipeId
            );

            return;
        }


        boolean saved = collectionService.saveRecipe(
                collectionId,
                recipeId
        );


        if (saved) {

            setFlash(
                    request,
                    "Recipe saved to your collection."
            );

        } else {

            setFlash(
                    request,
                    "Recipe is already saved in this collection."
            );
        }


        response.sendRedirect(
                request.getContextPath()
                        + "/recipe?id=" + recipeId
        );
    }


    /**
     * Removes a recipe from a collection.
     */
    private void removeRecipe(
            HttpServletRequest request,
            HttpServletResponse response,
            User user
    ) throws IOException {

        int collectionId = Integer.parseInt(
                request.getParameter("collectionId")
        );

        int recipeId = Integer.parseInt(
                request.getParameter("recipeId")
        );


        /*
         * Verify ownership before removing anything.
         */
        if (!isUserCollection(user.getId(), collectionId)) {

            setFlash(
                    request,
                    "You cannot modify this collection."
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/recipe?id=" + recipeId
            );

            return;
        }


        boolean removed = collectionService.removeRecipe(
                collectionId,
                recipeId
        );


        setFlash(
                request,
                removed
                        ? "Recipe removed from your collection."
                        : "Recipe was not saved in this collection."
        );


        response.sendRedirect(
                request.getContextPath()
                        + "/recipe?id=" + recipeId
        );
    }


    /**
     * Deletes a collection owned by the logged-in user.
     */
    private void deleteCollection(
            HttpServletRequest request,
            HttpServletResponse response,
            User user
    ) throws IOException {

        int collectionId = Integer.parseInt(
                request.getParameter("collectionId")
        );

        collectionService.deleteCollection(
                collectionId,
                user.getId()
        );

        setFlash(
                request,
                "Collection deleted successfully."
        );

        response.sendRedirect(
                request.getContextPath() + "/collections"
        );
    }

    /**
     * Confirms that a collection belongs to the logged-in user.
     */
    private boolean isUserCollection(
            int userId,
            int collectionId
    ) {

        return collectionService
                .getUserCollections(userId)
                .stream()
                .anyMatch(collection ->
                        collection.getId() == collectionId
                );
    }
}