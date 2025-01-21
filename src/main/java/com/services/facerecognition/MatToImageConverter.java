package com.services.facerecognition;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MatToImageConverter {

  public static Image matToImage(Mat mat) {
    if (mat == null || mat.empty()) {
      return null;
    }

    // Convertir BGR en RGB (JavaFX s'attend à des images RGB)
    Mat rgbMat = new Mat();
    Imgproc.cvtColor(mat, rgbMat, Imgproc.COLOR_BGR2RGB);

    int width = rgbMat.cols();
    int height = rgbMat.rows();
    WritableImage writableImage = new WritableImage(width, height);

    byte[] buffer = new byte[width * height * 3];
    rgbMat.get(0, 0, buffer);

    writableImage.getPixelWriter().setPixels(
        0, 0, width, height,
        PixelFormat.getByteRgbInstance(),
        buffer,
        0, width * 3);

    return writableImage;
  }
}
