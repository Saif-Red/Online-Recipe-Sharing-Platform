package com.recipehub.controller;

import com.recipehub.dao.RecipeDAO;
import com.recipehub.service.RecipeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/feed")
public class FeedServlet extends BaseServlet {
    private final RecipeService recipeService = new RecipeService(new RecipeDAO());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("q");
        String cuisine = request.getParameter("cuisine");
        try {
            request.setAttribute("recipes", recipeService.getFeed(search, cuisine));
            request.getRequestDispatcher("/WEB-INF/views/feed.jsp").forward(request, response);
        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/feed.jsp").forward(request, response);
        }
    }
}
