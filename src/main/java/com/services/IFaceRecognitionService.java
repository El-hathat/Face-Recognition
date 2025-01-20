package com.services;


import com.dao.entities.User;
import org.opencv.core.Mat;

public interface IFaceRecognitionService {
    User recognizeUser(Mat frame);

}
