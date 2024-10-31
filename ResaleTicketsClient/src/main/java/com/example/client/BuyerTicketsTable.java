package com.example.client;

import com.example.client.model.Ticket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.*;

public class BuyerTicketsTable extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Buying Tickets Table");

        Label descripitionSearchLabel = new Label("Search by description:");
        TextField descriptionField = new TextField();
        List<TextField> textFields = List.of(descriptionField);
        Layout.addTextFieldsToCSS(textFields);

        Button searchAllButton = new Button("Search All Tickets");
        Button searchByTextButton = new Button("Search");
        Button buyTicketButton = new Button("Buy Ticket");
        Layout.addLabelToCSS(descripitionSearchLabel);

        List<Button> buttons = List.of(searchAllButton, searchByTextButton, buyTicketButton);
        Layout.addButtonsToCSS(buttons);

        TableView<Ticket> tableView = Table.createTicketsTable();
        Table.putLabelOnTable(tableView, "There Are No Tickets For Sale.");

        VBox formLayout = new VBox(10);
        List<Node> nodes = List.of(descripitionSearchLabel, descriptionField, searchByTextButton, searchAllButton);

        Layout.setFormLayoutProperties(formLayout, nodes);
        HBox root = new HBox(20);
        root.setPadding(new Insets(10));

        root.getChildren().addAll(formLayout, tableView);

        Layout.createSceneForStage(primaryStage, root);

        searchAllButton.setOnAction(e -> {
            try {
                Table.setTicketsTableViewItems(formLayout, buyTicketButton, "", tableView);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        searchByTextButton.setOnAction(e -> {
            try {
                Table.setTicketsTableViewItems(formLayout, buyTicketButton, descriptionField.getText(), tableView);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (!formLayout.getChildren().contains(buyTicketButton)) {
                    formLayout.getChildren().add(buyTicketButton);
                }

                buyTicketButton.setOnAction(e -> {
                    ServerAccess.deleteFromServer(newSelection);
                    try {
                        Table.setTicketsTableViewItems(formLayout, buyTicketButton, "", tableView);
                        Layout.showMessage("Ticket Bought", "The purchase was successfully completed.", Alert.AlertType.INFORMATION);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        });
    }
}