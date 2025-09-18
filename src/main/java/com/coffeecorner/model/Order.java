package com.coffeecorner.model;

public class Order {
    private String name;
    private String contact;
    private String orderDetails;

    public Order(String name, String contact, String orderDetails) {
        this.name = name;
        this.contact = contact;
        this.orderDetails = orderDetails;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getOrderDetails() {
        return orderDetails;
    }
}
