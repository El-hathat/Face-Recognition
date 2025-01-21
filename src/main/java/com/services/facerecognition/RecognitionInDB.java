package com.services.facerecognition;

import com.dao.UserDaoImpl;
import com.dao.entities.User;
import org.opencv.core.Mat;
import java.util.List;

public class RecognitionInDB implements IFaceRecognitionService {
  TensorService tensorService = new TensorService();
  UserDaoImpl userService=new UserDaoImpl();
  public User recognizeUser(Mat frame) {
    List<User> users = userService.findAll();

      for (User user1:users) {
        String image = user1.getImagePath();
        if (tensorService.testFaces(tensorService.imageToMat(image), frame) && user1.isActive()) {
          return user1;

        }

      }



    return null;
  }
}
