package com.presentation.client.facescan;

import com.dao.entities.User;
import com.presentation.client.AppConfig;
import com.presentation.outils.SharedData;
import com.presentation.outils.navigation.Navigation;
import com.services.accesslog.IAccessLogService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.nio.file.Path;

public class FaceScanSuccessController {

    private int timeRemaining = 30;

    private User user;

    private IAccessLogService accessLogService = AppConfig.ACCESS_LOG_SERVICE;


    @FXML
    private ImageView userImage;

    @FXML
    private Label userName;

    @FXML
    private Label timerText;


    @FXML
    public void initialize() {

        user = (User) SharedData.getObject("user");

        accessLogService.logAccess(user);

        Platform.runLater(() -> {

            userName.setText(user.getName());
            userImage.setImage(new Image(Path.of(user.getImagePath()).toUri().toString()));

            startCountdown();
        });
    }

    private void startCountdown() {
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1), // Execute every second
                        event -> {
                            if (timeRemaining > 0) {
                                timeRemaining--;
                                timerText.setText(timeRemaining + "s");

                                if (timeRemaining == 0) {
                                    timerText.setText("Time's Up!");
                                    // Redirect to the welcome screen
                                    Navigation.goTo("main", "welcome");
                                }
                            }
                        }
                )
        );

        timeline.setCycleCount(timeRemaining); // Run for 30 cycles
        timeline.play();
    }

}
