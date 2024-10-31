package com.example.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        root.getStyleClass().add("hello-background");
        MainPageController mainPageController = fxmlLoader.getController();

        mainPageController.onStageShown(stage);
        stage.setTitle("Hello!");
        stage.setScene(new Scene(root, 1000, 500));
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}