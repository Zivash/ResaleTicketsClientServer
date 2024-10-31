package com.example.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private final String email;
    private String password;
    private String name;
    private String phoneNumber;
    private List<Ticket> ticketsForSale = new ArrayList<>();

    public User(String email) {
        this.email = email;
    }

    public User(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String password, String name, String phoneNumber, List<Ticket> ticketsForSale) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ticketsForSale = ticketsForSale;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}