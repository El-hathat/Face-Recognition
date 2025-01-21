package com.services.facerecognition;

import com.presentation.client.AppConfig;
import org.tensorflow.Tensor;
import org.tensorflow.Session;

import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.Graph;

public class TensorService {

  private Tensor<Float> createTensorForFaceNet(float[] imageData) {
    long[] shape = { 1, 160, 160, 3 }; // Forme attendue par FaceNet
    return Tensor.create(shape, FloatBuffer.wrap(imageData));
  }

  private float[] preprocessImageForFaceNet(Mat image) {
    if (image.empty()) {
      throw new IllegalArgumentException("Image vide ou non valide.");
    }

    // Étape 1 : Convertir en RGB si nécessaire
    Mat processedImage = new Mat();
    if (image.channels() == 1) { // Si l'image est en niveaux de gris
      Imgproc.cvtColor(image, processedImage, Imgproc.COLOR_GRAY2RGB);
    } else if (image.channels() == 3) { // Si l'image est déjà en couleur
      Imgproc.cvtColor(image, processedImage, Imgproc.COLOR_BGR2RGB);
    } else {
      throw new IllegalArgumentException("Image avec un format non pris en charge.");
    }

    // Étape 2 : Redimensionner à 160x160
    Mat resizedImage = new Mat();
    Imgproc.resize(processedImage, resizedImage, new org.opencv.core.Size(160, 160));

    // Étape 3 : Normaliser les pixels (0-255 → 0.0-1.0)
    Mat normalizedImage = new Mat();
    resizedImage.convertTo(normalizedImage, CvType.CV_32F, 1.0 / 255);

    // Étape 4 : Convertir en tableau de float
    int size = (int) (normalizedImage.total() * normalizedImage.channels());
    float[] floatArray = new float[size];
    normalizedImage.get(0, 0, floatArray);

    return floatArray;
  }

  private float[] runFaceNet(float[] imageData) {
    try (Graph graph = new Graph()) {
      // Charger le graphe du modèle depuis le fichier .pb
      byte[] graphDef = Files.readAllBytes(Paths.get(AppConfig.MODEL_PATH));
      graph.importGraphDef(graphDef);

      // Créer les tenseurs nécessaires
      long[] inputShape = { 1, 160, 160, 3 };
      try (Tensor<Float> inputTensor = Tensor.create(inputShape, FloatBuffer.wrap(imageData));
          Tensor<Boolean> phaseTrainTensor = (Tensor<Boolean>) Tensor.create(Boolean.FALSE); // Utiliser 0 pour False
          Session session = new Session(graph)) {

        // Exécuter le modèle pour obtenir les embeddings
        Tensor<?> output = session.runner()
            .feed("input", inputTensor) // Nom du nœud d'entrée pour l'image
            .feed("phase_train", phaseTrainTensor) // Nom du nœud pour phase_train
            .fetch("embeddings") // Nom du nœud de sortie pour les embeddings
            .run()
            .get(0);

        // Extraire les embeddings (vecteur de 128 dimensions)
        float[][] embeddings = new float[1][128]; // Adapter à la taille correcte
        output.copyTo(embeddings);
        return embeddings[0];
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erreur lors de l'exécution de FaceNet.", e);
    }
  }

  // Méthode utilitaire pour convertir byte[] en float[]
  // private float[] toFloatArray(byte[] byteArray) {
  // FloatBuffer floatBuffer =
  // ByteBuffer.wrap(byteArray).order(ByteOrder.nativeOrder()).asFloatBuffer();
  // float[] floatArray = new float[floatBuffer.remaining()];
  // floatBuffer.get(floatArray);
  // return floatArray;
  // }

  private double computeEuclideanDistance(float[] emb1, float[] emb2) {
    if (emb1.length != emb2.length) {
      throw new IllegalArgumentException("Les embeddings doivent avoir la même dimension.");
    }
    double sum = 0.0;
    for (int i = 0; i < emb1.length; i++) {
      sum += Math.pow(emb1[i] - emb2[i], 2);
    }
    return Math.sqrt(sum);
  }

  private boolean areFacesSame(float[] emb1, float[] emb2, double threshold) {
    double distance = computeEuclideanDistance(emb1, emb2);
    System.out.println("Distance entre les embeddings : " + distance);
    return distance < threshold;
  }

  public Mat imageToMat(String imagePath) {
    Mat localImage = Imgcodecs.imread(imagePath);
    if (localImage.empty()) {
      System.out.println("Erreur : Impossible de charger l'image.");

    }
    return localImage;
  }

  public boolean testFaces(Mat frame1, Mat frame2) {
    try {

      float[] imageData1 = preprocessImageForFaceNet(frame1);
      float[] imageData2 = preprocessImageForFaceNet(frame2);

      if (imageData1 == null || imageData2 == null) {
        System.out.println("Erreur : Prétraitement échoué.");

      }

      float[] embeddings1 = runFaceNet(imageData1);
      float[] embeddings2 = runFaceNet(imageData2);

      boolean areSame = areFacesSame(embeddings1, embeddings2, 0.46);
      System.out.println("Les visages sont les mêmes : " + areSame);
      return areSame;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
