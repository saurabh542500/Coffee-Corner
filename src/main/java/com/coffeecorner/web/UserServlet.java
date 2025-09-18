package com.coffeecorner.web;

import com.coffeecorner.dao.UserDAO;
import com.coffeecorner.model.User;
import com.coffeecorner.util.DBConnection;
import com.coffeecorner.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            if ("register".equalsIgnoreCase(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String dob = request.getParameter("dob");
                String address = request.getParameter("address");
                String password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirm_password");

                // Password validation
                if (!password.equals(confirmPassword)) {
                    response.sendRedirect("register-failed.html");
                    return;
                }

                // Check if email exists
                if (userDAO.emailExists(email)) {
                    response.sendRedirect("register-failed.html");
                    return;
                }

                // Hash password
                String hashedPassword = PasswordUtil.hashPassword(password);

                // Create user object
                User user = new User(name, email, dob, address, hashedPassword);

                boolean success = userDAO.registerUser(user);
                if (success) {
                    response.sendRedirect("register-success.html");
                } else {
                    response.sendRedirect("register-failed.html");
                }

            } else if ("login".equalsIgnoreCase(action)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                // Hash input password
                String hashedPassword = PasswordUtil.hashPassword(password);

                boolean valid = userDAO.checkLogin(email, hashedPassword);
                if (valid) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userEmail", email);
                    response.sendRedirect("index.html");
                } else {
                    response.sendRedirect("login_register.html");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
