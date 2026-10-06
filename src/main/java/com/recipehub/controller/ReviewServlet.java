package com.recipehub.controller;

import com.recipehub.service.ReviewService;
import com.recipehub.dao.ReviewDAO;
import com.recipehub.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends BaseServlet {

    private final ReviewService reviewService =
            new ReviewService(new ReviewDAO());

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        if (currentUser(request) == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp"
            );
            return;
        }

        try {
            int recipeId = Integer.parseInt(
                    request.getParameter("recipeId")
            );

            int rating = Integer.parseInt(
                    request.getParameter("rating")
            );

            String reviewText =
                    request.getParameter("reviewText");

            int userId = currentUser(request).getId();

            reviewService.saveReview(
                    recipeId,
                    userId,
                    rating,
                    reviewText
            );

            setFlash(request, "Your review has been saved.");

            response.sendRedirect(
                    request.getContextPath()
                            + "/recipe?id=" + recipeId
            );

        } catch (NumberFormatException e) {

            setFlash(request, "Invalid recipe or rating.");

            response.sendRedirect(
                    request.getContextPath() + "/feed"
            );

        } catch (ValidationException e) {

            setFlash(request, e.getMessage());

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
}