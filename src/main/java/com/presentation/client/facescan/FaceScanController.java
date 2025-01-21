package com.presentation.client.facescan;

import com.dao.entities.User;
import com.presentation.client.AppConfig;
import com.presentation.outils.SharedData;
import com.presentation.outils.navigation.Navigation;
import com.services.facerecognition.IFaceRecognitionService;
import com.services.facerecognition.MatToImageConverter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.opencv.core.Mat;

public class FaceScanController implements FaceRecognitionListener {

    private IFaceRecognitionService faceRecognitionService = AppConfig.FACE_RECOGNITION_SERVICE;

    private Thread facescanThread;

    private volatile boolean isRecognizing = false;


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

        if (isRecognizing) {
            return;
        }

        isRecognizing = true;

        showLoadingAlert("Recognizing user...");

        Thread recognizeUserThread = new Thread(() -> {

            User user = faceRecognitionService.recognizeUser(image);

            if (user != null) {

                SharedData.putObject("user", user);
                showSuccessAlert("User recognized");

                facescanThread.interrupt();

                Platform.runLater(() -> Navigation.goTo("main", "facescan:success"));

            }else {
                showLoadingAlert("Scanning for faces...");
                isRecognizing = false;
            }
        });

        recognizeUserThread.setDaemon(true);
        recognizeUserThread.start();

    }

    @Override
    public void onFrameUpdated(Mat frame) {
        Platform.runLater(() -> imageView.setImage(matToImage(frame)));
    }

    private void startFaceScan() {

        facescanThread = new Thread(new FaceScan(this));
        facescanThread.setDaemon(true);
        facescanThread.start();

        showLoadingAlert("Scanning for faces...");
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

    private void showLoadingAlert(String message) {
        Platform.runLater(() -> {
            alertSpinner.setVisible(true);
            alertMessage.setText(message);
            alertContainer.getStyleClass().removeAll("alert-danger", "alert-success");
            alertContainer.setVisible(true);
        });
    }
}
