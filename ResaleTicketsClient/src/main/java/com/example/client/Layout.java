package com.example.client;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class Layout {

    public static void setFormLayoutProperties(VBox formLayout, List<Node> nodes) {
        formLayout.setPadding(new Insets(10));
        for (Node node : nodes){
            formLayout.getChildren().add(node);
        }

        VBox.setMargin(formLayout.getChildren().getLast(), new Insets(50,0,0,0));
    }

    public static void addButtonsToCSS(List<Button> buttons){
        for (Button button : buttons){
            button.getStyleClass().add("button-style");
        }
    }

    public static void addLabelToCSS(Label label){
        label.getStyleClass().add("text-label-style");
    }

    public static void addTextFieldsToCSS(List<TextField> textFields){
        for (TextField textField : textFields){
            textField.getStyleClass().add("fields-style");
        }
    }

    public static void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void createSceneForStage(Stage primaryStage, HBox root) {
        Scene scene = new Scene(root, 1100, 500);
        scene.getStylesheets().add(Objects.requireNonNull(Layout.class.getResource("/styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}