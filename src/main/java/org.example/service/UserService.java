package org.example.service;

import org.example.dao.UserDAOImpl;
import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.Connection;
import java.sql.SQLException;
public class UserService {
    private final UserDao userDAO;

    public UserService() throws SQLException {
        Connection conn = null;
        this.userDAO = new UserDAOImpl(null);
    }
    public boolean register(String username, String password, String role) throws SQLException {
        try {
            User existingUser = userDAO.getUserByUsername(username);
            if (existingUser != null) {
                System.out.println("❌ Username already exists. Please try a different one.");
                return false;
            }
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setRole(role.toUpperCase());
            userDAO.addUser(newUser);
            System.out.println("✅ Registered successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("⚠️ Database error during registration: " + e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) throws SQLException {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                return null;
            }
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Database error during login: " + e.getMessage());
            return null;
        }
    }
}