package com.presentation.admin.dashboard;

import com.presentation.admin.AppConfig;
import com.services.auth.AdminSession;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class DashboardController {

    @FXML
    private Label userCount;

    @FXML
    private Label accessLogsCount;

    @FXML
    public void initialize() {
             // Set the user count
        Platform.runLater(() -> {
            userCount.setText(String.valueOf(AppConfig.USERS_SERVICE.getUserCount()));
            accessLogsCount.setText(String.valueOf(AppConfig.ACCESS_LOG_SERVICE.getAccessLogCount()));
        });
    }
}
