package com.services.users;

import com.dao.entities.User;

import java.util.List;

public interface IUsersService {

    List<User> getAllUsers();

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    User getUser(long id);

}
