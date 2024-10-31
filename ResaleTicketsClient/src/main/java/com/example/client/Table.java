package com.example.client;

import com.example.client.model.Ticket;
import com.example.client.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Table {

    public static TableView<Ticket> createTicketsTable() {
        TableView<Ticket> tableView = new TableView<>();
        TableColumn<Ticket, String> showNameColumn = new TableColumn<>("Show Name");
        TableColumn<Ticket, String> dateColumn = new TableColumn<>("Date");
        TableColumn<Ticket, String> priceColumn = new TableColumn<>("Price");
        TableColumn<Ticket, Integer> categoryColumn = new TableColumn<>("Category");
        TableColumn<Ticket, String> sectionColumn = new TableColumn<>("Section");
        TableColumn<Ticket, String> rowColumn = new TableColumn<>("Row");
        TableColumn<Ticket, String> seatColumn = new TableColumn<>("Seat");
        TableColumn<Ticket, Integer> descriptionColumn = new TableColumn<>("Description");

        showNameColumn.setCellValueFactory(new PropertyValueFactory<>("showName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        rowColumn.setCellValueFactory(new PropertyValueFactory<>("row"));
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seat"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.getStyleClass().add("table-style");
        showNameColumn.getStyleClass().add("showName");
        dateColumn.getStyleClass().add("date");
        sectionColumn.getStyleClass().add("littleColumn");
        rowColumn.getStyleClass().add("littleColumn");
        seatColumn.getStyleClass().add("littleColumn");
        priceColumn.getStyleClass().add("littleColumn");
        categoryColumn.getStyleClass().add("categoryColumn");
        descriptionColumn.getStyleClass().add("description");

        tableView.getColumns().addAll(showNameColumn, dateColumn, priceColumn, categoryColumn, sectionColumn, rowColumn,
                seatColumn, descriptionColumn);

        return tableView;
    }

    public static TableView<User> createUsersTable() {
        TableView<User> tableView = new TableView<>();
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        TableColumn<User, Integer> phoneColumn = new TableColumn<>("Phone Number");


        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tableView.getStyleClass().add("table-style");
        emailColumn.getStyleClass().add("table-user-column-width");
        passwordColumn.getStyleClass().add("table-user-column-width");
        nameColumn.getStyleClass().add("table-user-column-width");
        phoneColumn.getStyleClass().add("table-user-column-width");

        tableView.getColumns().addAll(emailColumn, passwordColumn, nameColumn, phoneColumn);

        return tableView;
    }

    public static void putLabelOnTable(TableView tableView, String label) {
        Label customPlaceholder = new Label(label);
        tableView.setPlaceholder(customPlaceholder);
    }

    public static void setTicketsTableViewItems(VBox formLayout, Button removeButton, String description, TableView<Ticket> tableView) throws IOException {
        formLayout.getChildren().remove(removeButton);
        ObservableList<Ticket> tickets = FXCollections.observableList(ServerAccess.getTickets(description));
        tableView.setItems(tickets);
    }

    public static void setUsersTableViewItems(VBox formLayout, Button removeButton, TableView<User> tableView) throws IOException {
        formLayout.getChildren().remove(removeButton);
        ObservableList<User> users = FXCollections.observableList(ServerAccess.getUser(""));
        tableView.setItems(users);
    }
}