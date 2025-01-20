package com.services.users;

import com.dao.IUserDao;
import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.services.ConfigServices;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        String newUserImagePath = copyUserImage(user.getImagePath());

        if (newUserImagePath == null) {
            return false;
        }

        user.setImagePath(newUserImagePath);
        return userDao.save(user);
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) {

        String imagePath = oldUser.getImagePath();

        String oldImagePathUri = Paths.get(oldUser.getImagePath()).toUri().toString();
        String newImagePathUri = newUser.getImagePath();

        if (!oldImagePathUri.equals(newImagePathUri)) {

            // copy the new image
            imagePath = copyUserImage(newUser.getImagePath());
            if (imagePath == null) {
                System.out.println("Failed to copy the new image");
                return false;
            }
            deleteImage(oldUser.getImagePath());
        }

        newUser.setImagePath(imagePath);

        return userDao.update(newUser);
    }

    @Override
    public boolean deleteUser(User user) {
        if (userDao.delete(user)) {
            // delete user image from application data
            deleteImage(user.getImagePath());
            return true;
        }
        return false;
    }

    @Override
    public User getUser(long id) {
        return userDao.findOne(id);
    }

    @Override
    public int getUserCount() {
        return userDao.count();
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

    private void deleteImage(String imagePath) {
        File image = new File(imagePath);
        if (image.exists() && image.isFile()) {
            if (!image.delete()) {
                // TODO: log error indicating that the image was not deleted
            }
        }
    }

    private String copyUserImage(String from) {

        String imagePathStr = null;

        File sourceImage = new File(from);

        if (sourceImage.exists() && sourceImage.isFile()) {
            // Define the default image directory
            File defaultDir = new File(AppConfig.USERS_IMAGE_PATH);
            if (!defaultDir.exists()) {
                defaultDir.mkdirs();
            }

            // Generate a random name for the image
            String randomName = UUID.randomUUID() + getFileExtension(sourceImage.getName());
            imagePathStr = AppConfig.USERS_IMAGE_PATH + randomName;

            // Copy the image
            File destFile = new File(imagePathStr);
            try {
                Files.copy(sourceImage.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                // TODO: log error indicating that the image was not copied
                return null;
            }

        }

        return imagePathStr;
    }


}
