package com.coffeecorner.model;

public class User {
    private String fullName;
    private String email;
    private String dob;
    private String address;
    private String password;

    public User(String fullName, String email, String dob, String address, String password) {
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.address = address;
        this.password = password;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getDob() { return dob; }
    public String getAddress() { return address; }
    public String getPassword() { return password; }
}
