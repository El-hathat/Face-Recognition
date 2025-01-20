package com.presentation.client.facescan;

import com.dao.entities.User;
import com.facerecognition.MatToImageConverter;
import com.presentation.client.AppConfig;
import com.presentation.outils.SharedData;
import com.presentation.outils.navigation.Navigation;
import com.services.facerecognition.IFaceRecognitionService;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.opencv.core.Mat;

public class FaceScanController implements FaceRecognitionListener {

    private IFaceRecognitionService faceRecognitionService = AppConfig.FACE_RECOGNITION_SERVICE;

    private FaceScan faceScan;

    @FXML
    private ImageView imageView;

    @FXML
    private Text alertMessage;

    @FXML
    private ProgressIndicator alertSpinner;

    @FXML
    private HBox alertContainer;

    @FXML
    public void initialize() {
        Platform.runLater(() -> startFaceScan());
    }


    @Override
    public void onFaceDetected(Mat image) {

        faceScan.stop();

        showLoadingAlert("Recognizing user...");


        User user = faceRecognitionService.recognizeUser(image);

        if (user != null) {
            SharedData.putObject("user", user);
            showSuccessAlert("User recognized");
        } else {
            showErrorAlert("User not recognized");
            startFaceScan();
            return;
        }

        Platform.runLater(() -> imageView.setImage(matToImage(image)));

        // Create a PauseTransition for 2 seconds
        // 2 seconds to mitigate the impact of password brute force attacks
        PauseTransition pause = new PauseTransition(Duration.seconds(10));
        pause.setOnFinished(event2 -> {


            Platform.runLater(() -> Navigation.goTo("main", "facescan:success"));


        });
        // Start the transition
        pause.play();


    }

    @Override
    public void onFrameUpdated(Mat frame) {
        Platform.runLater(() -> imageView.setImage(matToImage(frame)));
    }

    private void startFaceScan() {
        faceScan = new FaceScan(this);
        new Thread(faceScan).start();
    }

    private Image matToImage(Mat mat) {
        return MatToImageConverter.matToImage(mat);
    }

    private void showInfoAlert(String message) {
        Platform.runLater(() -> {
            alertSpinner.setVisible(false);
            alertMessage.setText(message);
            alertContainer.getStyleClass().removeAll("alert-danger", "alert-success");
            alertContainer.setVisible(true);
        });
    }

    private void showSuccessAlert(String message) {
        Platform.runLater(() -> {

            alertSpinner.setVisible(false);
            alertMessage.setText(message);
            alertContainer.getStyleClass().add("alert-success");
            alertContainer.setVisible(true);
        });
    }

    private void showErrorAlert(String message) {
        Platform.runLater(() -> {
            alertSpinner.setVisible(false);
            alertMessage.setText(message);
            alertContainer.getStyleClass().add("alert-danger");
            alertContainer.setVisible(true);
        });
    }

    private void showLoadingAlert(String message){
        Platform.runLater(() -> {
            alertSpinner.setVisible(true);
            alertMessage.setText(message);
            alertContainer.getStyleClass().removeAll("alert-danger", "alert-success");
            alertContainer.setVisible(true);
        });
    }
}
