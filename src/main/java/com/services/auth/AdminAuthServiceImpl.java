package com.services.auth;

import com.dao.IAdminDao;
import com.dao.entities.Admin;
import com.services.ConfigServices;

import java.security.NoSuchAlgorithmException;

public class AdminAuthServiceImpl implements IAdminAuthService {

    private final AdminSession session = AdminSession.getInstance();
    private IAdminDao adminDao;

    public AdminAuthServiceImpl() {
        this.adminDao = ConfigServices.ADMIN_DAO;
    }

    @Override
    public boolean login(String username, String password) {

        // begin login proccess

        // get salt
        String salt = adminDao.getSalt(username);
        if (salt == null) {
            return false;
        }

        // hash password
        try {
            String saltHashPassword = PasswordHashing.hashPassword(password, salt);

            // find user by username and password
            Admin admin = adminDao.findByUsernamePassword(username, saltHashPassword);
            if (admin != null) {

                session.setAdmin(admin);

                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        return false;
    }

    @Override
    public boolean isLogged() {
        return session.getAdmin() == null;
    }
}
