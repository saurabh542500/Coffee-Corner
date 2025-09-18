package com.coffeecorner.dao;

import com.coffeecorner.model.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingDAO {
    private Connection conn;

    public BookingDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO table_booking (name, phone, date, time_slot, guests) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getName());
            stmt.setString(2, booking.getPhone());
            stmt.setString(3, booking.getDate());
            stmt.setString(4, booking.getTimeSlot());
            stmt.setInt(5, booking.getGuests());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
