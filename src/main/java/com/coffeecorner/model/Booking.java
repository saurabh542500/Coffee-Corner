package com.coffeecorner.model;

public class Booking {
    private String name;
    private String phone;
    private String date;
    private String timeSlot;
    private int guests;

    public Booking(String name, String phone, String date, String timeSlot, int guests) {
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.timeSlot = timeSlot;
        this.guests = guests;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getDate() { return date; }
    public String getTimeSlot() { return timeSlot; }
    public int getGuests() { return guests; }
}
