package com.presentation.admin;

import com.dao.IUserDao;
import com.dao.UserDaoImpl;
import com.services.auth.AdminAuthServiceImpl;
import com.services.auth.IAdminAuthService;

public class AppConfig {

    public static final String APP_NAME = "Admin App";

    public static final int WINDOW_HEIGHT = 580;

    public static final int WINDOW_WIDTH = 900;

    public static final String ADMIN_USERNAME_REGEX = "[a-zA-Z0-9_]{3,}";

    public static final String ADMIN_PASSWORD_REGEX = "[a-zA-Z0-9_]{8,}";

    public static final String EMAIL_REGEX = "[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+";
    public static final String PHONE_NUMBER_REGEX = "0[67][0-9]{8}";
    public static final String NAME_REGEX = "[a-zA-Z]{3,40}";
    public static final String PASSCODE_REGEX = "[0-9]{4,6}";

    public static final IAdminAuthService ADMIN_AUTH_SERVICE = new AdminAuthServiceImpl();

    public static final IUserDao USER_DAO = new UserDaoImpl();


}
