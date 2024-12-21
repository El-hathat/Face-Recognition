package com.presentation.admin;

import com.services.auth.AdminAuthServiceImpl;
import com.services.auth.IAdminAuthService;

public class AppConfig {

    public static final String APP_NAME = "Admin App";

    public static final int WINDOW_HEIGHT = 580;

    public static final int WINDOW_WIDTH = 900;

    public static final String ADMIN_USERNAME_REGEX = "[a-zA-Z0-9_]{3,}";

    public static final String ADMIN_PASSWORD_REGEX = "[a-zA-Z0-9_]{8,}";

    public static final IAdminAuthService ADMIN_AUTH_SERVICE = new AdminAuthServiceImpl();


}
