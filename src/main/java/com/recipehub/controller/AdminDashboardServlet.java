package com.recipehub.controller;

import com.recipehub.dao.RecipeDAO;
import com.recipehub.dao.ReviewDAO;
import com.recipehub.dao.UserDAO;
import com.recipehub.model.Recipe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {

    private final UserDAO userDAO = new UserDAO();
    private final RecipeDAO recipeDAO = new RecipeDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try {
            request.setAttribute(
                    "users",
                    userDAO.findAll()
            );

            List<Recipe> recipes =
                    recipeDAO.findForModeration();

            request.setAttribute(
                    "recipes",
                    recipes
            );

            long publishedCount =
                    recipes.stream()
                            .filter(recipe ->
                                    "APPROVED".equals(
                                            recipe.getStatus().name()
                                    )
                            )
                            .count();

            long pendingCount =
                    recipes.stream()
                            .filter(recipe ->
                                    "PENDING".equals(
                                            recipe.getStatus().name()
                                    )
                            )
                            .count();

            request.setAttribute(
                    "publishedCount",
                    publishedCount
            );

            request.setAttribute(
                    "pendingCount",
                    pendingCount
            );

            request.setAttribute(
                    "reviewCount",
                    reviewDAO.countAll()
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/admin-dashboard.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Dashboard data could not be loaded."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/admin-dashboard.jsp"
            ).forward(request, response);
        }
    }
}