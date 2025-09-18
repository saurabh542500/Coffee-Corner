	package com.coffeecorner.util;


import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Connection successful!");
                conn.close();
            } else {
                System.out.println("❌ Connection failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}