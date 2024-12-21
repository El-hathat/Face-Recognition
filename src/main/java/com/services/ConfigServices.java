package com.services;

import com.dao.AdminDaoImpl;
import com.dao.IAdminDao;
import com.facerecognition.RecognitionInDB;

public class ConfigServices {

    public static final String HASH_ALGORITHM = "SHA-256";

    public static final int SALT_LENGTH = 16;

    public static final IAdminDao ADMIN_DAO = new AdminDaoImpl();

    public static final IFaceRecognitionService FACERECOGNITIONIMPL=new RecognitionInDB();

}
