package com.coffeecorner.web;

import com.coffeecorner.model.Booking;
import com.coffeecorner.dao.BookingDAO;
import com.coffeecorner.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (Connection conn = DBConnection.getConnection()) {
            BookingDAO bookingDAO = new BookingDAO(conn);

            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String date = request.getParameter("date");
            String timeSlot = request.getParameter("time");
            int guests = Integer.parseInt(request.getParameter("guests"));

            Booking booking = new Booking(name, phone, date, timeSlot, guests);

            boolean success = bookingDAO.addBooking(booking);

            if (success) {
                response.sendRedirect("booking_success.html");
            } else {
                response.sendRedirect("booking_failed.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("booking_failed.html");
        }
    }
}
