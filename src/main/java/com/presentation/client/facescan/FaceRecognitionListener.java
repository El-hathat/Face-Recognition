package com.presentation.client.facescan;

import com.dao.entities.User;
import javafx.scene.image.Image;
import org.opencv.core.Mat;


public interface FaceRecognitionListener {

    void onFaceDetected(Mat image);

    void onFrameUpdated(Mat frame);

}
