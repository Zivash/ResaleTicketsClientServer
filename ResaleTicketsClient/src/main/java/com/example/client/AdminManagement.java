package com.example.client;

import com.example.client.model.Ticket;
import com.example.client.model.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AdminManagement extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Admin Sign In");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(40);
        grid.setVgap(15);

        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");

        Button signInButton = new Button("Sign In");
        Button usersButton = new Button("Show All Users");
        Button ticketsButton = new Button("Show All Tickets");

        List<Button> buttons = List.of(signInButton, usersButton, ticketsButton);
        Layout.addButtonsToCSS(buttons);

        List<TextField> textFields = List.of(userNameField, passwordField);
        Layout.addTextFieldsToCSS(textFields);

        grid.add(userNameField, 1, 0);
        grid.add(passwordField, 1, 1);
        grid.add(signInButton, 1, 2);

        signInButton.setOnAction(event -> {
            if (userNameField.getText().equals("admin") && passwordField.getText().equals("admin")) {
                grid.getChildren().clear();
                grid.add(usersButton, 1, 0);
                grid.add(ticketsButton, 1, 1);
                Button backButton = new Button("Back");

                usersButton.setOnAction(e -> {
                    Stage UsersStage = new Stage();
                    UsersStage.setTitle("Admin Users Table");
                    UsersStage.setX(50);
                    UsersStage.setY(50);

                    Button deleteUserButton = new Button("Delete User");
                    List<Button> buttonsList = List.of(deleteUserButton, backButton);
                    Layout.addButtonsToCSS(buttonsList);

                    VBox formLayout = new VBox(10);
                    List<Node> nodes = List.of(backButton);

                    Layout.setFormLayoutProperties(formLayout, nodes);
                    HBox root = new HBox(20);
                    root.setPadding(new Insets(10));
                    TableView<User> tableView = Table.createUsersTable();
                    Table.putLabelOnTable(tableView, "We don't have any users in our system.");

                    try {
                        Table.setUsersTableViewItems(formLayout, deleteUserButton, tableView);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    backButton.setOnAction(e3 -> UsersStage.close());

                    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                            if (!formLayout.getChildren().contains(deleteUserButton)) {
                                formLayout.getChildren().setAll(
                                        deleteUserButton,
                                        backButton
                                );
                            }

                            deleteUserButton.setOnAction(e1 -> {
                                ServerAccess.deleteFromServer(newSelection);
                                try {
                                    Table.setUsersTableViewItems(formLayout, deleteUserButton, tableView);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                        }
                    });

                    root.getChildren().addAll(formLayout, tableView);
                    Layout.createSceneForStage(UsersStage, root);
                });

                ticketsButton.setOnAction(e -> {
                    Stage TicketsStage = new Stage();
                    TicketsStage.setTitle("Admin Tickets Table");
                    TicketsStage.setX(50);
                    TicketsStage.setY(50);

                    Button deleteTicketButton = new Button("Delete Ticket");
                    List<Button> buttonsList = List.of(deleteTicketButton, backButton);
                    Layout.addButtonsToCSS(buttonsList);

                    VBox formLayout = new VBox(10);
                    List<Node> nodes = List.of(backButton);

                    Layout.setFormLayoutProperties(formLayout, nodes);
                    HBox root = new HBox(20);
                    root.setPadding(new Insets(10));
                    TableView<Ticket> tableView = Table.createTicketsTable();
                    Table.putLabelOnTable(tableView, "We don't have any tickets to sell in our system.");

                    try {
                        Table.setTicketsTableViewItems(formLayout, deleteTicketButton, "", tableView);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    backButton.setOnAction(e3 -> TicketsStage.close());

                    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                            if (!formLayout.getChildren().contains(deleteTicketButton)) {
                                formLayout.getChildren().setAll(
                                        deleteTicketButton,
                                        backButton
                                );
                            }

                            deleteTicketButton.setOnAction(e1 -> {
                                ServerAccess.deleteFromServer(newSelection);
                                try {
                                    Table.setTicketsTableViewItems(formLayout, deleteTicketButton, "", tableView);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                        }
                    });

                    root.getChildren().addAll(formLayout, tableView);
                    Layout.createSceneForStage(TicketsStage, root);
                });
            } else {
                Layout.showMessage("Wrong Username/Password", "Wrong Username or Password. Please Try Again.", Alert.AlertType.ERROR);
            }
        });

        Scene scene = new Scene(grid, 300, 200);
        signInButton.requestFocus();
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}