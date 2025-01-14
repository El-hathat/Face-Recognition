package com.services.auth;

import com.dao.entities.Admin;

public class AdminSession {

    private static AdminSession instance = null;
    private Admin admin;

    private AdminSession() {
    }

    public static AdminSession getInstance() {
        if (instance == null) {
            instance = new AdminSession();
        }
        return instance;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public static void clearSession() {
        instance = null;
    }

    public static boolean isLoggedIn() {
        return instance != null;
    }
}
