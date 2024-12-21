package com.presentation.admin;

import com.services.auth.AdminSession;
import com.services.auth.IAdminAuthService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class AdminApp extends Application {

    public static Stage primaryStage = null;

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;

        // Load the reusable component from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/admin/auth/auth.fxml"));


        // Load the root layout from the FXML
        Pane root = loader.load();

        // Set up the scene and stage
        Scene scene = new Scene(root, AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
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
