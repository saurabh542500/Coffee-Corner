package com.coffeecorner.dao;

import com.coffeecorner.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean placeOrder(Order order) {
        String sql = "INSERT INTO orders (name, contact, order_details) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getName());
            stmt.setString(2, order.getContact());
            stmt.setString(3, order.getOrderDetails());

            int rowsInserted = stmt.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
