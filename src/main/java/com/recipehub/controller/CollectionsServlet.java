package com.recipehub.controller;

import com.recipehub.dao.CollectionDAO;
import com.recipehub.exception.ValidationException;
import com.recipehub.model.Collection;
import com.recipehub.model.User;
import com.recipehub.service.CollectionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/collections")
public class CollectionsServlet extends BaseServlet {

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

            List<Collection> collections =
                    collectionService.getUserCollections(user.getId());

            request.setAttribute(
                    "collections",
                    collections
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/collections.jsp"
            ).forward(request, response);

        } catch (ValidationException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }
}