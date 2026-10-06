package com.recipehub.controller;

import com.recipehub.dao.UserDAO;
import com.recipehub.service.AuthService;
import com.recipehub.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    private final AuthService authService = new AuthService(new UserDAO());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> user;
        try {
            user = authService.login(email, password);
        } catch (RuntimeException e) {
            e.printStackTrace();

            String errorMessage = e.getClass().getSimpleName()
                    + " - " + e.getMessage();

            if (e.getCause() != null) {
                errorMessage += " | Cause: "
                        + e.getCause().getClass().getSimpleName()
                        + " - " + e.getCause().getMessage();
            }

            request.setAttribute("error", "Login error: " + errorMessage);

            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(request, response);
            return;
        }

        if (user.isEmpty()) {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("loggedInUser", user.get());
        response.sendRedirect(request.getContextPath() + "/feed");
    }
}
