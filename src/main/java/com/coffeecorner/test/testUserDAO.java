package com.coffeecorner.test;

import com.coffeecorner.dao.UserDAO;
import com.coffeecorner.model.User;
import com.coffeecorner.util.DBConnection;
import com.coffeecorner.util.PasswordUtil;

import java.sql.Connection;

public class testUserDAO {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            // 🔍 Test emailExists
            String testEmail = "testuser@example.com";
            boolean exists = userDAO.emailExists(testEmail);
            System.out.println("Email exists: " + exists);

            // 📝 Test registerUser
            if (!exists) {
                String hashedPassword = PasswordUtil.hashPassword("secure123");
                User newUser = new User("Test User", testEmail, "1995-08-31", "123 Coffee Lane", hashedPassword);
                boolean registered = userDAO.registerUser(newUser);
                System.out.println("User registered: " + registered);
            }

            // 🔐 Test checkLogin
            String loginPassword = PasswordUtil.hashPassword("secure123");
            boolean loginValid = userDAO.checkLogin(testEmail, loginPassword);
            System.out.println("Login valid: " + loginValid);

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
