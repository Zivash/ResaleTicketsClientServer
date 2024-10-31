package dm;

import java.io.Serializable;

public class Ticket implements Serializable {
    private final String id;
    private final double price;
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

    public Ticket(String id, double price, String showName, String category, String date, int section, int row, int seat, String description, String email) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public String getShowName() {
        return showName;
    }

    public String getCategory() {
        return category;
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

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return id + "," + price + "," + showName + "," + category + "," + date + "," + section +
                "," + row + "," + seat + "," + description + "," + email;

    }
}