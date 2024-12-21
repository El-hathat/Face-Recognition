package com.services.users;

import com.dao.IUserDao;
import com.dao.entities.User;
import com.services.ConfigServices;

import java.util.List;

public class UsersServiceImpl implements IUsersService {

    IUserDao userDao = ConfigServices.USER_DAO;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean addUser(User user) {
        return userDao.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean deleteUser(User user) {
        return userDao.delete(user);
    }

    @Override
    public User getUser(long id) {
        return userDao.findOne(id);
    }
}
