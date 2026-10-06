package com.recipehub.filter;

import com.recipehub.model.User;
import com.recipehub.model.UserRole;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/contributor/*", "/explorer/*", "/admin/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute("loggedInUser");

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        String path = httpRequest.getRequestURI();
        if (path.contains("/admin/") && user.getRole() != UserRole.ADMIN) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required.");
            return;
        }
        if (path.contains("/contributor/") && user.getRole() != UserRole.CONTRIBUTOR) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Contributor access required.");
            return;
        }
        if (path.contains("/explorer/") && user.getRole() != UserRole.EXPLORER) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Explorer access required.");
            return;
        }

        chain.doFilter(request, response);
    }
}
