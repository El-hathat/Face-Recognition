package com.services.users;

import com.dao.IUserDao;
import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.services.ConfigServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

public class UsersServiceImpl implements IUsersService {

    IUserDao userDao = ConfigServices.USER_DAO;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.findAll();
        return (users == null ? List.of() : users);
    }

    @Override
    public boolean addUser(User user) {

        String imagePathStr = null;

        File imagePath = new File(user.getImagePath());
        if (imagePath.exists() && imagePath.isFile()) {
            // Define the default image directory
            File defaultDir = new File(AppConfig.USERS_IMAGE_PATH);
            if (!defaultDir.exists()) {
                defaultDir.mkdirs();
            }

            // Generate a random name for the image
            String randomName = UUID.randomUUID() + getFileExtension(imagePath.getName());
            imagePathStr = AppConfig.USERS_IMAGE_PATH + randomName;

            // Copy the image
            File destFile = new File(imagePathStr);
            try {
                Files.copy(imagePath.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // TODO: log error indicating that the image was not copied
                return false;
            }

        } else {
            return false;
        }

        user.setImagePath(imagePathStr);

        return userDao.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean deleteUser(User user) {
        if (userDao.delete(user)) {
            // delete user image from application data
            File file = new File(user.getImagePath());

            if (!file.delete()) {
                // TODO: log error indicating that the image was not deleted
            }

            return true;
        }
        return false;
    }

    @Override
    public User getUser(long id) {
        return userDao.findOne(id);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}
