package com.coffeecorner.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestOrderServlet")
public class TestOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String contact = request.getParameter("contact");

        String[] items = request.getParameterValues("items");
        StringBuilder orderDetails = new StringBuilder();

        if (items != null) {
            for (String item : items) {
                String qtyParam = request.getParameter(item + "_qty");
                int qty = 1;
                if (qtyParam != null && !qtyParam.isEmpty()) {
                    try {
                        qty = Integer.parseInt(qtyParam);
                        if (qty < 1) qty = 1;
                    } catch (NumberFormatException e) {
                        qty = 1;
                    }
                }
                orderDetails.append(item).append(" x ").append(qty).append("; ");
            }
        }

        System.out.println("Customer Name: " + name);
        System.out.println("Contact: " + contact);
        System.out.println("Order Details: " + orderDetails.toString());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO orders (name, contact, order_details) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setString(3, orderDetails.toString());

            int rowsInserted = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if (rowsInserted > 0) {
                out.println("<h2>Order placed successfully!</h2>");
            } else {
                out.println("<h2>Order failed to save.</h2>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h2>Error occurred: " + e.getMessage() + "</h2>");
        }
    }
}
