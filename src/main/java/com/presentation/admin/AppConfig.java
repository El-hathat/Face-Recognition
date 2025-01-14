package com.presentation.admin;

import com.services.auth.AdminAuthServiceImpl;
import com.services.auth.IAdminAuthService;
import com.services.users.IUsersService;
import com.services.users.UsersServiceImpl;

public class AppConfig {

    public static final String APP_NAME = "Admin App";

    public static final int WINDOW_HEIGHT = 620;

    public static final int WINDOW_WIDTH = 900;

    public static final String EMAIL_REGEX = "[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]+";
    public static final String PHONE_NUMBER_REGEX = "0[67][0-9]{8}";
    public static final String NAME_REGEX = "[a-zA-Z -]{3,40}";
    public static final String PASSCODE_REGEX = "[0-9]{4,6}|.{0}";

    public static final String USERS_IMAGE_PATH = "data/images/users/";

    public static final IAdminAuthService ADMIN_AUTH_SERVICE = new AdminAuthServiceImpl();

    public static final IUsersService USERS_SERVICE = new UsersServiceImpl();


}
