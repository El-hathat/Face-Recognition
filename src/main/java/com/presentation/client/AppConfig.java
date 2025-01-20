package com.presentation.client;

import com.dao.entities.User;
import com.services.IFaceRecognitionService;
import com.services.accesslog.AccessLogServiceImpl;
import com.services.accesslog.IAccessLogService;
import com.services.auth.AdminAuthServiceImpl;
import com.services.auth.IAdminAuthService;
import com.services.users.IUsersService;
import com.services.users.UsersServiceImpl;
import org.opencv.core.Mat;

import static java.lang.Thread.sleep;

public class AppConfig {

    public static final String APP_NAME = "Client App";

    public static final int WINDOW_HEIGHT = 620;

    public static final int WINDOW_WIDTH = 900;

    public static final String USERS_IMAGE_PATH = "data/images/users/";

    public static final IUsersService USERS_SERVICE = new UsersServiceImpl();

    public static final String CASCADE_PATH = "src/main/resources/com/files/haarcascade_frontalface_alt.xml";

    public static final IAccessLogService ACCESS_LOG_SERVICE = new AccessLogServiceImpl();

    public static final IFaceRecognitionService FACE_RECOGNITION_SERVICE = null;
}
