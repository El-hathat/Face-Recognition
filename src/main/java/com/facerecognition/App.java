package com.facerecognition;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class App extends Application {

  private VideoCapture camera;
  private CascadeClassifier faceDetector;

  @Override
  public void start(Stage stage) {
    // Charger la bibliothèque native OpenCV
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    // Initialiser la caméra et le détecteur de visages
    camera = new VideoCapture(0); // 0 pour la caméra par défaut
    if (!camera.isOpened()) {
      System.err.println("Erreur : Impossible d'ouvrir la caméra.");
      System.exit(1);
    }

    faceDetector = new CascadeClassifier("facerecognition/src/main/resources/com/files/haarcascade_frontalface_alt.xml");
    if (faceDetector.empty()) {
      System.err.println("Erreur : Impossible de charger le fichier cascade.");
      System.exit(1);
    }

    // Créer l'interface utilisateur avec JavaFX
    ImageView imageView = new ImageView();
    StackPane root = new StackPane(imageView);
    Scene scene = new Scene(root, 640, 480);

    stage.setTitle("Détection de Visages avec JavaFX et OpenCV");
    stage.setScene(scene);
    stage.show();

    // Lancer le flux vidéo dans un thread séparé
    new Thread(() -> {
      Mat frame = new Mat();
      while (camera.isOpened()) {
        if (camera.read(frame)) {
          detectAndDisplay(frame);
          imageView.setImage(MatToImageConverter.matToImage(frame));
        } else {
          System.err.println("Erreur : Impossible de lire la frame de la caméra.");
          break;
        }
      }
    }).start();
  }

  public void detectAndDisplay(Mat frame) {
    Mat grayFrame = new Mat();
    Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY); // Convert to grayscale
    Imgproc.equalizeHist(grayFrame, grayFrame); // Equalize histogram

    // Use MatOfRect to store detected faces
    MatOfRect faces = new MatOfRect();
    faceDetector.detectMultiScale(
        grayFrame, // Input image (grayscale)
        faces, // Output object containing detected faces
        1.1, // Scale factor
        3, // Min neighbors
        0, // Flags
        new Size(30, 30), // Minimum face size
        new Size() // Maximum face size
    );

    // Iterate over all detected faces and draw rectangles
    for (Rect face : faces.toArray()) {
      Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);
    }
  }

  @Override
  public void stop() {
    if (camera != null) {
      camera.release();
      System.out.println("Caméra libérée.");
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
