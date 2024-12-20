package com.presentation.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    public Label center;

    DashboardController(){
        center.setText("CR7 : MY NAME IS THE BEST");
    }
}
