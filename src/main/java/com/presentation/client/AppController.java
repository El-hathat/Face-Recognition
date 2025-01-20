package com.presentation.client;

import com.presentation.outils.navigation.Navigation;
import com.presentation.outils.navigation.RouteGroup;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class AppController {

    @FXML
    private HBox navHost;


    @FXML
    public void initialize() {

        RouteGroup mainRoutes = new RouteGroup("main", navHost, false);
        mainRoutes.addRoute("welcome", "/com/client/welcome/welcome.fxml");
        mainRoutes.addRoute("facescan", "/com/client/facescan/facescan.fxml");
        mainRoutes.addRoute("facescan:success", "/com/client/facescan/face-scan-success.fxml");

        // add the main route group to the navigation
        Navigation.addRouteGroup(mainRoutes);

        // load the welcome route
        Platform.runLater(() -> Navigation.goTo("main", "welcome"));
    }

}
