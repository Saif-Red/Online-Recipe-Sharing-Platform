package com.recipehub.controller;

import com.recipehub.dao.RecipeDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.exception.ValidationException;
import com.recipehub.service.RecipeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/moderate-recipe")
public class AdminRecipeModerationServlet extends BaseServlet {

    private final RecipeService recipeService =
            new RecipeService(new RecipeDAO());

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try {
            String recipeIdParameter =
                    request.getParameter("recipeId");

            String status =
                    request.getParameter("status");

            if (recipeIdParameter == null
                    || recipeIdParameter.isBlank()) {

                throw new ValidationException(
                        "Recipe ID is required."
                );
            }

            int recipeId;

            try {
                recipeId =
                        Integer.parseInt(recipeIdParameter);
            } catch (NumberFormatException e) {
                throw new ValidationException(
                        "Invalid recipe ID."
                );
            }

            recipeService.updateRecipeStatus(
                    recipeId,
                    status
            );

            String message;

            if ("APPROVED".equalsIgnoreCase(status)) {
                message =
                        "Recipe approved successfully.";
            } else {
                message =
                        "Recipe rejected successfully.";
            }

            setFlash(request, message);

        } catch (ValidationException | DataAccessException e) {

            e.printStackTrace();

            setFlash(
                    request,
                    e.getMessage()
            );
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/dashboard"
        );
    }
}