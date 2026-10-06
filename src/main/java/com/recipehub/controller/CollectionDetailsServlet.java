package com.recipehub.controller;

import com.recipehub.dao.CollectionDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.Collection;
import com.recipehub.model.Recipe;
import com.recipehub.model.User;
import com.recipehub.service.CollectionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/collection-details")
public class CollectionDetailsServlet extends BaseServlet {

    private final CollectionService collectionService =
            new CollectionService(new CollectionDAO());

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        User user = currentUser(request);

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        try {

            int collectionId = Integer.parseInt(
                    request.getParameter("id")
            );

            if (collectionId <= 0) {
                throw new ValidationException(
                        "Invalid collection."
                );
            }

            Collection collection =
                    findUserCollection(
                            user.getId(),
                            collectionId
                    );

            if (collection == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Collection not found."
                );
                return;
            }

            List<Recipe> recipes =
                    collectionService.getRecipesInCollection(
                            collectionId
                    );

            request.setAttribute(
                    "collection",
                    collection
            );

            request.setAttribute(
                    "recipes",
                    recipes
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/collection-details.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid collection id."
            );

        } catch (ValidationException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );

        } catch (DataAccessException e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/collection-details.jsp"
            ).forward(request, response);
        }
    }


    /**
     * Finds a collection only if it belongs to the logged-in user.
     */
    private Collection findUserCollection(
            int userId,
            int collectionId
    ) {

        List<Collection> collections =
                collectionService.getUserCollections(userId);

        return collections.stream()
                .filter(collection ->
                        collection.getId() == collectionId
                )
                .findFirst()
                .orElse(null);
    }
}