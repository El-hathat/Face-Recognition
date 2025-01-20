package com.presentation.client.facescan;

import com.dao.entities.User;
import com.presentation.client.AppConfig;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class FaceScan implements Runnable {

    static {
        // Load OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private volatile boolean running = true;
    private VideoCapture camera;
    private CascadeClassifier faceCascade;
    private FaceRecognitionListener faceRecognitionListener;

    {
        // Create VideoCapture object to access webcam
        camera = new VideoCapture(0);

        if (!camera.isOpened()) {
            System.out.println("Error: Camera not opened!");
        }

        // Load the pre-trained Haar Cascade classifier for face detection
        faceCascade = new CascadeClassifier(AppConfig.CASCADE_PATH);

        if (faceCascade.empty()) {
            System.out.println("Error: CascadeClassifier not loaded.");
        }

    }

    public FaceScan(FaceRecognitionListener faceRecognitionListener) {
        this.faceRecognitionListener = faceRecognitionListener;
    }

    @Override
    public void run() {

        Mat frame = new Mat();
        User user = null;

        while (running) {
            // Capture a frame from the webcam
            camera.read(frame);

            if (frame.empty()) {
                System.out.println("Error: Empty frame.");
                break;
            }

            // Convert frame to grayscale for better face detection performance
            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            // Detect faces
            Rect[] facesArray = detectFaces(gray, faceCascade);

            // If faces are detected, crop and save the face region
            if (facesArray != null) {
                for (Rect face : facesArray) {
                    // Draw a rectangle around the face
                    Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
                }

                // Notify the listener that a face has been detected
                faceRecognitionListener.onFaceDetected(frame);

            }

            // display frame
            faceRecognitionListener.onFrameUpdated(frame);

        }

        // Release resources when stopping
        camera.release();
        System.out.println("FaceScan thread stopped.");
    }

    public void stop() {
        running = false;
    }

    private Rect[] detectFaces(Mat gray, CascadeClassifier faceCascade) {
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(gray, faces);
        return faces.toArray();
    }

}
