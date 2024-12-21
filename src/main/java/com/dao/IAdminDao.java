package com.dao;

import com.dao.entities.Admin;

public interface IAdminDao {

    Admin findByUsername(String username);

    String getSalt(String username);

    Admin findByUsernamePassword(String username , String saltHashPassword);

}
