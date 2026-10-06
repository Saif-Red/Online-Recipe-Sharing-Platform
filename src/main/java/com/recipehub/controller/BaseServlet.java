package com.recipehub.controller;

import com.recipehub.model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseServlet extends HttpServlet {
    protected User currentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("loggedInUser");
    }

    protected void setFlash(HttpServletRequest request, String message) {
        request.getSession().setAttribute("flashMessage", message);
    }
}
