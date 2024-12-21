package com.presentation.admin.auth;

import com.presentation.admin.AdminApp;
import com.presentation.admin.AppConfig;
import com.services.auth.IAdminAuthService;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

public class AuthController {


    private IAdminAuthService authService = AppConfig.ADMIN_AUTH_SERVICE;

    @FXML
    public HBox alertContainer;

    @FXML
    public Text alertMessage;

    @FXML
    public Button loginBtn;

    @FXML
    public ProgressIndicator loginSpinner;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void login(ActionEvent event) {

        username.setDisable(true);
        password.setDisable(true);
        loginBtn.setDisable(true);

        showInfoAlert("Connexion en cours...");

        // Create a PauseTransition for 2 seconds
        // 2 seconds to mitigate the impact of password brute force attacks
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event2 -> {

            username.setDisable(false);
            password.setDisable(false);
            loginBtn.setDisable(false);

            String usernameStr = username.getText();
            String passwordStr = password.getText();


            /*try {
                String salt = PasswordHashing.generateSalt();
                String hashedPassword = PasswordHashing.hashPassword(passwordStr, salt);

                System.out.println("Salt: " + salt);
                System.out.println("Hashed Password: " + hashedPassword);


            } catch (NoSuchAlgorithmException e) {
                showErrorAlert(e.getMessage());
            }*/ // Commented out for the sake of the demo

            if (authService.login(usernameStr, passwordStr)) {

                showSuccessAlert("Connexion réussie, redirection vers le tableau de bord");

                PauseTransition goToAdmin = new PauseTransition(Duration.seconds(.5));
                goToAdmin.setOnFinished(event3 -> goToAdminApp());
                goToAdmin.play();

            } else {
                showErrorAlert("Nom d'utilisateur ou mot de passe incorrect");
            }

        });
        // Start the transition
        pause.play();
    }

    private void goToAdminApp() {
        if (authService.isLogged()) {
            showErrorAlert("Vous n'êtes pas connecté");
            return;
        }
        // Load the admin app
        try {
            // Load the second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/admin/app.fxml"));
            Scene scene = new Scene(loader.load(), AppConfig.WINDOW_WIDTH, AppConfig.WINDOW_HEIGHT);
            // Set up the scene and stage
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(getClass().getResource("/com/css/styles.css").toExternalForm());

            // Get the current stage
            if (AdminApp.primaryStage != null)
                AdminApp.primaryStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void showInfoAlert(String message) {
        alertMessage.setText(message);
        alertContainer.getStyleClass().removeAll("alert-danger", "alert-success");
        alertContainer.setVisible(true);
    }

    private void showSuccessAlert(String message) {
        alertMessage.setText(message);
        alertContainer.getStyleClass().add("alert-success");
        alertContainer.setVisible(true);
    }

    private void showErrorAlert(String message) {
        alertMessage.setText(message);
        alertContainer.getStyleClass().add("alert-danger");
        alertContainer.setVisible(true);
    }

    private void hideAlert() {
        alertContainer.setVisible(false);
    }

    private boolean isLoginValid(String username, String password) {
        return username.matches(AppConfig.ADMIN_USERNAME_REGEX) && password.matches(AppConfig.ADMIN_PASSWORD_REGEX);
    }
}
