package com.presentation.admin;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class AppController {

    @FXML
    public BorderPane mainBorderPane;

    @FXML
    public void initialize() {
        // This method is automatically called after the FXML is loaded
        dashboardOnAction(null);
    }

    @FXML
    void dashboardOnAction(ActionEvent event) {
        loadFXMLContent("/com/admin/dashboard.fxml");
    }

    @FXML
    void usersOnAction(ActionEvent event) {
        loadFXMLContent("/com/admin/users/users.fxml");
    }

    @FXML
    void reportsOnAction(ActionEvent event) {

    }

    @FXML
    void settingsOnAction(ActionEvent event) {

    }

    private void loadFXMLContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newContent = loader.load();
            mainBorderPane.setCenter(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
