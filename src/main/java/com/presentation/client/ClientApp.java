package com.presentation.client;


import com.presentation.outils.navigation.Navigation;
import com.presentation.outils.navigation.RouteGroup;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class ClientApp extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        HBox rootHost = new HBox();

        // create app route group
        RouteGroup appRoutes = new RouteGroup("app", rootHost, false);
        appRoutes.addRoute("main", "/com/client/app.fxml");

        // Add the route group to the navigation
        Navigation.addRouteGroup(appRoutes);

        // Load the auth route
        Navigation.goTo("app", "main");

        // Set up the scene and stage
        Scene scene = new Scene(rootHost, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets().add(getClass().getResource("/com/css/styles.css").toExternalForm());


        stage.setTitle(AppConfig.APP_NAME);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
