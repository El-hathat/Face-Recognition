package com.presentation.admin.dashboard;

import com.services.auth.AdminSession;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class DashboardController {

    @FXML
    public Text textCenter;

    @FXML
    public void initialize() {
        // This method is automatically called after the FXML is loaded
        textCenter.setText(String.format("Welcome %s, to the Dashboard", AdminSession.getInstance().getAdmin().getUsername()));
    }
}
