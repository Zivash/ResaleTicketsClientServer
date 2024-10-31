package com.example.client;

import com.example.client.model.Ticket;
import com.example.client.model.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SellerTicketsTable extends Application {
    private ObservableList<Ticket> userTickets = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Selling Tickets Table");

        TextField signInEmailField = new TextField();
        signInEmailField.setPromptText("Enter email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        TextField signUpEmailField = new TextField();
        signUpEmailField.setPromptText("Enter email");
        TextField newPriceField = new TextField();
        newPriceField.setPromptText("Enter New Price");
        List<TextField> textFields = List.of(signInEmailField, passwordField, signUpEmailField, newPriceField);
        Layout.addTextFieldsToCSS(textFields);
        Label welcomeLabel = new Label("Welcome!");

        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");
        Button deleteTicketButton = new Button("Delete Ticket");
        Button editPriceTicketButton = new Button("Edit Price");
        Label welcomeUserLabel = new Label();
        Button addTicketButton = new Button("Add a new Ticket");
        Button backButton = new Button("Back");
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        Layout.addLabelToCSS(welcomeLabel);
        Layout.addLabelToCSS(welcomeUserLabel);

        List<Button> buttons = List.of(signInButton, signUpButton, deleteTicketButton,editPriceTicketButton, addTicketButton, backButton, confirmButton, cancelButton);
        Layout.addButtonsToCSS(buttons);

        TableView<Ticket> tableView = Table.createTicketsTable();
        Table.putLabelOnTable(tableView, "You Don't Have Tickets For Sale.");

        VBox formLayout = new VBox(10);
        List<Node> nodesForLayout = List.of(welcomeLabel, signInEmailField, passwordField, signInButton, signUpEmailField, signUpButton);
        Layout.setFormLayoutProperties(formLayout, nodesForLayout);
        VBox.setMargin(signUpEmailField, new Insets(50, 0, 0, 0));
        VBox.setMargin(signUpButton, new Insets(0));


        VBox formLayoutSignIn = new VBox(10);
        nodesForLayout = List.of(welcomeUserLabel, addTicketButton, backButton);
        Layout.setFormLayoutProperties(formLayoutSignIn, nodesForLayout);

        VBox formLayoutEditPrice = new VBox(10);


        List<Node> nodes = List.of(newPriceField,confirmButton,cancelButton);
        Layout.setFormLayoutProperties(formLayoutEditPrice,nodes);


        HBox root = new HBox(20);
        root.setPadding(new Insets(10));

        root.getChildren().addAll(formLayout, tableView);

        Layout.createSceneForStage(primaryStage, root);
        signInButton.requestFocus();

        signInButton.setOnAction(e -> {
            formLayoutSignIn.getChildren().removeAll(deleteTicketButton, editPriceTicketButton);
            String email = signInEmailField.getText();
            String password = passwordField.getText();
            try {
                String validEmail = HandleInput.emailInput(email);
                if (validEmail.isEmpty()) {
                    User user = ServerAccess.getUser(email).getFirst();
                    if (null == user) {
                        Layout.showMessage("Sign Up Required", "Email not found. Please Sign Up.", Alert.AlertType.INFORMATION);
                        handleSignUp(root, formLayout, tableView, email);

                    } else {
                        if (user.getPassword().equals(password)) {
                            Layout.showMessage("Sign In Succeeded", "You Are Signed In To The System.", Alert.AlertType.INFORMATION);
                            root.getChildren().setAll(formLayoutSignIn, tableView);
                            welcomeUserLabel.setText("Welcome " + user.getName() + "!");
                            try {
                                userTickets = FXCollections.observableList(ServerAccess.getUserTickets(user));
                                tableView.setItems(userTickets);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                            backButton.setOnAction(e2 -> {
                                ObservableList<Ticket> emptyList = FXCollections.observableArrayList();
                                tableView.setItems(emptyList);
                                root.getChildren().setAll(formLayout, tableView);
                            });

                            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                                if (newSelection != null) {
                                    if (!formLayoutSignIn.getChildren().contains(deleteTicketButton)) {
                                        formLayoutSignIn.getChildren().setAll(
                                                addTicketButton,
                                                deleteTicketButton,
                                                editPriceTicketButton,
                                                backButton
                                        );
                                    }


                                    deleteTicketButton.setOnAction(e2 -> {
                                        formLayoutSignIn.getChildren().removeAll(deleteTicketButton, editPriceTicketButton);
                                        ServerAccess.deleteFromServer(newSelection);
                                        try {
                                            userTickets = FXCollections.observableList(ServerAccess.getUserTickets(ServerAccess.getUser(newSelection.getEmail()).getFirst()));
                                            tableView.setItems(userTickets);
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    });

                                    editPriceTicketButton.setOnAction(e2 -> {
                                        root.getChildren().setAll(formLayoutEditPrice, tableView);
                                        confirmButton.requestFocus();

                                        confirmButton.setOnAction(e3 -> {
                                            String price = newPriceField.getText();
                                            if(HandleInput.doubleInput("Price",price).isEmpty()){
                                                double newPrice = Double.parseDouble(newPriceField.getText());
                                                if(newSelection.getPrice() != newPrice) {
                                                    newSelection.setPrice(newPrice);
                                                    ServerAccess.update(newSelection);
                                                    try {
                                                        userTickets = FXCollections.observableList(ServerAccess.getUserTickets(ServerAccess.getUser(newSelection.getEmail()).getFirst()));
                                                    } catch (IOException ex) {
                                                        throw new RuntimeException(ex);
                                                    }

                                                    Layout.showMessage("Price Changed", "The price has been successfully changed.", Alert.AlertType.INFORMATION);
                                                    tableView.setItems(userTickets);
                                                }
                                                else {
                                                    Layout.showMessage("Same Price Input","Ticket price is the same as before.\nPlease Enter A New Price", Alert.AlertType.ERROR);
                                                }
                                            }
                                            else{
                                                Layout.showMessage("Invalid Price Input","Invalid Price Input.\nPlease Enter A Valid Price", Alert.AlertType.ERROR);
                                            }
                                        });
                                        cancelButton.setOnAction(e3 -> {
                                            tableView.getSelectionModel().clearSelection();
                                            tableView.refresh();
                                            root.getChildren().setAll(formLayoutSignIn, tableView);
                                            formLayoutSignIn.getChildren().removeAll(deleteTicketButton, editPriceTicketButton);
                                        });
                                    });
                                }
                            });

                            addTicketButton.setOnAction(e3 -> {
                                VBox formLayoutAddTicket = new VBox(10);

                                formLayoutAddTicket.setPadding(new Insets(10));
                                TextField priceField = new TextField();
                                priceField.setPromptText("Enter price");
                                TextField showNameField = new TextField();
                                showNameField.setPromptText("Enter show name");
                                TextField categoryField = new TextField();
                                categoryField.setPromptText("Enter category");
                                TextField dateField = new TextField();
                                dateField.setPromptText("Enter date");
                                TextField sectionField = new TextField();
                                sectionField.setPromptText("Enter section");
                                TextField rowField = new TextField();
                                rowField.setPromptText("Enter row");
                                TextField seatField = new TextField();
                                seatField.setPromptText("Enter seat");
                                TextField descriptionField = new TextField();
                                descriptionField.setPromptText("Enter description");

                                List<TextField> textFieldsForAdd = List.of(priceField, showNameField, categoryField, dateField, sectionField,
                                        rowField, seatField, descriptionField);

                                Layout.addTextFieldsToCSS(textFieldsForAdd);

                                Button addButton = new Button("Add Ticket");
                                Button backSignInButton = new Button("Back");

                                List<Button> buttonsAddTickets = List.of(addButton, backSignInButton);
                                Layout.addButtonsToCSS(buttonsAddTickets);

                                List<Node> nodes2 = List.of(priceField, showNameField, categoryField, dateField, sectionField, rowField,
                                        seatField, descriptionField, addButton, backSignInButton);
                                Layout.setFormLayoutProperties(formLayoutAddTicket, nodes2);

                                root.getChildren().setAll(formLayoutAddTicket, tableView);
                                addButton.requestFocus();

                                addButton.setOnAction(e4 -> {
                                    String price = priceField.getText();
                                    String showName = showNameField.getText();
                                    String category = categoryField.getText();
                                    String date = dateField.getText();
                                    String section = sectionField.getText();
                                    String row = rowField.getText();
                                    String seat = seatField.getText();
                                    String description = descriptionField.getText();
                                    String errorInfo = HandleInput.addingTicketValidInput(price, showName, category,
                                            date, section, row, seat);

                                    if (errorInfo.isEmpty()) {
                                        double doublePrice = Double.parseDouble(priceField.getText());
                                        int intSection = Integer.parseInt(sectionField.getText());
                                        int intRow = Integer.parseInt(rowField.getText());
                                        int intSeat = Integer.parseInt(seatField.getText());

                                        Ticket ticket = new Ticket(doublePrice, showName, category, date, intSection,
                                                intRow, intSeat, description, email);

                                        try {
                                            if (!isTicketExist(ticket)) {
                                                ServerAccess.addToServer(ticket);
                                                Layout.showMessage("Ticket Added", "Ticket Addition Is Done.", Alert.AlertType.INFORMATION);
                                                root.getChildren().setAll(formLayoutSignIn, tableView);
                                                try {
                                                    userTickets = FXCollections.observableList(ServerAccess.getUserTickets(user));
                                                    tableView.setItems(userTickets);
                                                } catch (IOException ex) {
                                                    throw new RuntimeException(ex);
                                                }
                                            } else {
                                                Layout.showMessage("Ticket Already Exist", "Ticket Already Exist. Please Add New Ticket.", Alert.AlertType.ERROR);
                                            }
                                        } catch (IOException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else {
                                        Layout.showMessage("Invalid Input", errorInfo, Alert.AlertType.ERROR);
                                    }
                                });

                                backSignInButton.setOnAction(e2 -> root.getChildren().setAll(formLayoutSignIn, tableView));
                            });
                        } else {
                            Layout.showMessage("Password Incorrect", "Please Enter A Correct Password.", Alert.AlertType.ERROR);
                        }
                    }
                } else {
                    Layout.showMessage("Email Format Incorrect", "Please Enter A Valid Email.", Alert.AlertType.ERROR);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        signUpButton.setOnAction(e -> {
            String email = signUpEmailField.getText();
            try {
                String validEmail = HandleInput.emailInput(email);
                if (validEmail.isEmpty()) {
                    User user = ServerAccess.getUser(email).getFirst();
                    if (null == user) {
                        handleSignUp(root, formLayout, tableView, email);
                    } else {
                        Layout.showMessage("Email Exist", "The Email Already Exists.", Alert.AlertType.ERROR);
                    }
                } else {
                    Layout.showMessage("Email Format Incorrect", "Please Enter A Valid Email.", Alert.AlertType.ERROR);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleSignUp(HBox root, VBox formLayout, TableView tableView, String email)
    {
        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter your name");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        TextField userPhoneField = new TextField();
        userPhoneField.setPromptText("Enter your phone number");
        Button backToMainLayoutButton = new Button("Back");
        Button registerButton = new Button("Register");

        List<Button> buttons = List.of(backToMainLayoutButton,registerButton);
        Layout.addButtonsToCSS(buttons);

        List<TextField> fields = List.of(passwordField, userNameField,userPhoneField);
        Layout.addTextFieldsToCSS(fields);

        VBox formLayoutSignUp = new VBox(10);
        List<Node> nodes = List.of(userNameField,userPhoneField,passwordField,registerButton,backToMainLayoutButton);
        Layout.setFormLayoutProperties(formLayoutSignUp,nodes);

        root.getChildren().setAll(formLayoutSignUp, tableView);
        registerButton.requestFocus();

        registerButton.setOnAction(e2 -> {
            String name = userNameField.getText();
            String phoneNumber = userPhoneField.getText();
            String password = passwordField.getText();

            String errorInfo = HandleInput.signUpValidInput(password,name,phoneNumber);

            if(errorInfo.isEmpty()) {
                User newUser = new User(email, password, name, phoneNumber);
                ServerAccess.addToServer(newUser);
                Layout.showMessage("Sign Up Succeeded", "You Are Signed Up To The System.", Alert.AlertType.INFORMATION);
                root.getChildren().setAll(formLayout, tableView);
            }
            else
            {
                Layout.showMessage("Invalid Input", errorInfo, Alert.AlertType.ERROR);
            }
        });

        backToMainLayoutButton.setOnAction(e2 -> root.getChildren().setAll(formLayout, tableView));
    }

    private static boolean isTicketExist(Ticket newTicket) throws IOException {
        List<Ticket> allTickets = ServerAccess.getTickets("");

        for (Ticket ticket : allTickets) {
            if (ticket.getShowName().equals(newTicket.getShowName()) &&
                    ticket.getDate().equals(newTicket.getDate()) &&
                    ticket.getSection() == newTicket.getSection() &&
                    ticket.getRow() == newTicket.getRow() &&
                    ticket.getSeat() == newTicket.getSeat()){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        launch();
    }
}