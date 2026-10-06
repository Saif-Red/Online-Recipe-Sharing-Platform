package com.recipehub.controller;

import com.recipehub.dao.RecipeDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.model.Recipe;
import com.recipehub.model.User;
import com.recipehub.service.RecipeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/contributor/dashboard")
public class ContributorDashboardServlet extends BaseServlet {

    private final RecipeService recipeService =
            new RecipeService(new RecipeDAO());

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
            List<Recipe> recipes =
                    recipeService.getMyRecipes(user.getId());

            request.setAttribute("myRecipes", recipes);

            request.getRequestDispatcher(
                    "/WEB-INF/views/contributor-dashboard.jsp"
            ).forward(request, response);

        } catch (DataAccessException e) {

            e.printStackTrace();

            setFlash(
                    request,
                    "Your recipes could not be loaded."
            );

            response.sendRedirect(
                    request.getContextPath() + "/feed"
            );
        }
    }
}