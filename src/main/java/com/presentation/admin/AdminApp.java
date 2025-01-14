package com.presentation.admin;

import com.presentation.admin.navigation.Navigation;
import com.presentation.admin.navigation.Route;
import com.presentation.admin.navigation.RouteGroup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class AdminApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {


        HBox rootHost = new HBox();

        // create auth route group
        RouteGroup authRoutes = new RouteGroup("auth", rootHost, false);
        authRoutes.addRoute(new Route("login", "/com/admin/auth/auth.fxml", false));

        // create app route group
        RouteGroup appRoutes = new RouteGroup("app", rootHost, true);
        appRoutes.addRoute(new Route("main", "/com/admin/app.fxml"));

        // Add the route group to the navigation
        Navigation.addRouteGroups(authRoutes, appRoutes);

        // Load the auth route
        Navigation.goTo("auth", "login");

        // Set up the scene and stage
        Scene scene = new Scene(rootHost, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/com/css/styles.css").toExternalForm());


        stage.setTitle(AppConfig.APP_NAME);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }


}
