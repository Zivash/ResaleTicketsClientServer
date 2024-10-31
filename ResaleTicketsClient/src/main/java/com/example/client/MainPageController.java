package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @FXML
    private ImageView backGroundImage;
    @FXML

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String imagePath = Paths.get("src", "main", "resources", "HelloBackground.jpg").toAbsolutePath().toString();
        Image image = new Image("file:///" + imagePath);
        backGroundImage.setImage(image);
    }

    public void onStageShown(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) -> backGroundImage.setFitWidth(newVal.doubleValue()));

        stage.heightProperty().addListener((obs, oldVal, newVal) -> backGroundImage.setFitHeight(newVal.doubleValue()));
    }

    @FXML
    protected void onBuyerButton() {
        Stage stage = new Stage();
        BuyerTicketsTable buyerTicketsTable = new BuyerTicketsTable();
        buyerTicketsTable.start(stage);
    }

    @FXML
    protected void onAdminButton() {
        Stage stage = new Stage();
        AdminManagement adminManagement = new AdminManagement();
        adminManagement.start(stage);
    }

    @FXML
    protected void onSellerButton() {
        Stage stage = new Stage();
        SellerTicketsTable sellerTicketsTable = new SellerTicketsTable();
        sellerTicketsTable.start(stage);
    }
}