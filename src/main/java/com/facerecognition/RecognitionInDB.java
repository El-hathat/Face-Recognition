package com.facerecognition;

import com.dao.UserDaoImpl;
import com.dao.entities.User;
import com.services.IFaceRecognitionService;
import org.opencv.core.Mat;
import java.util.List;

public class RecognitionInDB implements IFaceRecognitionService {
  TensorService tensorService = new TensorService();
  UserDaoImpl userService;
  public User recognizeUser(Mat frame) {
    User user = null;
    List<User> users = userService.findAll();

      for (User user1:users) {
        String image = user1.getImagePath();
        if (tensorService.testFaces(tensorService.imageToMat(image), frame)) {
          user=user1;
          break;
        }
      }



    return user;
  }
}
