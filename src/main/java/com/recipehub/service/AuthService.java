package com.recipehub.service;

import com.recipehub.dao.UserDAO;
import com.recipehub.exception.DataAccessException;
import com.recipehub.model.User;
import com.recipehub.util.PasswordUtil;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> login(String email, String rawPassword) {
        if (email == null || rawPassword == null || email.isBlank() || rawPassword.isBlank()) {
            return Optional.empty();
        }
        try {
            Optional<User> user = userDAO.findByEmail(email.trim().toLowerCase());
            if (user.isPresent() && user.get().getPasswordHash().equals(PasswordUtil.sha256(rawPassword))) {
                return user;
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Login could not be completed.", e);
        }
    }
}
