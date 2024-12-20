package com.presentation.admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AdminApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Load the reusable component from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/admin/app.fxml"));


// Load the root layout from the FXML
        BorderPane root = loader.load();

// Set up the scene and stage
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Reusable FXML Component");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
