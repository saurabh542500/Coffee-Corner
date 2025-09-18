package com.coffeecorner.web;

import com.coffeecorner.dao.OrderDAO;
import com.coffeecorner.model.Order;
import com.coffeecorner.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@SuppressWarnings("serial")
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

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
                    try { qty = Integer.parseInt(qtyParam); } 
                    catch (NumberFormatException e) { qty = 1; }
                    if (qty < 1) qty = 1;
                }
                orderDetails.append(item).append(" x ").append(qty).append("; ");
            }
        }

        try (Connection conn = DBConnection.getConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            Order order = new Order(name, contact, orderDetails.toString());

            boolean success = orderDAO.placeOrder(order);

            if (success) {
                response.sendRedirect("Online_Order_Success.html");
            } else {
                response.sendRedirect("Online_Order_Failed.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Online_Order_Failed.html");
        }
    }
}
