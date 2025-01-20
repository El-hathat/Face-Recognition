package com.presentation.client.welcome;

import com.presentation.outils.navigation.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class WelcomeController {
    @FXML
    void onActionLogin(ActionEvent event) {
        Navigation.goTo("main", "facescan");
    }

    @FXML
    void handleEnterKeyPress(ActionEvent event) {

    }
}
