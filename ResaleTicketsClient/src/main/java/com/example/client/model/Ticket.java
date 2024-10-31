package com.example.client.model;

import java.io.Serializable;

public class Ticket implements Serializable {
    private final String id;
    private double price;
    private final String showName;
    private final String category;
    private final String date;
    private final int section;
    private final int row;
    private final int seat;
    private final String description;
    private final String email;

    public Ticket(double price, String showName, String category, String date, int section, int row, int seat, String description, String email) {
        this.id = String.valueOf(hashCode());
        this.price = price;
        this.showName = showName;
        this.category = category;
        this.date = date;
        this.section = section;
        this.row = row;
        this.seat = seat;
        this.description = description;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getShowName() {
        return showName;
    }

    public String getDate() {
        return date;
    }

    public int getSection() {
        return section;
    }

    public int getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }
}