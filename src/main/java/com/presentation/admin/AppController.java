package com.presentation.admin;


import com.presentation.outils.navigation.Navigation;
import com.presentation.outils.navigation.RouteGroup;
import com.services.auth.AdminSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class AppController {

    @FXML
    private Button buttonUsers;

    @FXML
    private Button buttonAccess;

    @FXML
    private Button buttonDashboard;

    @FXML
    private HBox navHost;

    @FXML
    public void initialize() {
        // This method is automatically called after the FXML is loaded

        RouteGroup mainRoutes = new RouteGroup("main", navHost, true);

        // add routes to the main route group
        mainRoutes.addRoute("dashboard", "/com/admin/dashboard/dashboard.fxml");
        mainRoutes.addRoute("users", "/com/admin/users/users.fxml");
        mainRoutes.addRoute("access", "/com/admin/access/access.fxml");

        // add the main route group to the navigation
        Navigation.addRouteGroup(mainRoutes);

        // load the dashboard
        dashboardOnAction(null);

    }

    @FXML
    void dashboardOnAction(ActionEvent event) {
        selectButton(buttonDashboard);
        Navigation.goTo("main", "dashboard");
    }

    @FXML
    void usersOnAction(ActionEvent event) {
        selectButton(buttonUsers);
        Navigation.goTo("main", "users");
    }

    @FXML
    void accessOnAction(ActionEvent event) {
        selectButton(buttonAccess);
        Navigation.goTo("main", "access");
    }


    @FXML
    void logoutOnAction(ActionEvent event) {
        // Clear the session
        AdminSession.clearSession();

        // Load the login page
        Navigation.goTo("auth", "login");
    }

    private void selectButton(Button button) {
        buttonDashboard.getStyleClass().remove("selected");
        buttonUsers.getStyleClass().remove("selected");
        buttonAccess.getStyleClass().remove("selected");

        button.getStyleClass().add("selected");
    }
}
