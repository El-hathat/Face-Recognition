package com.dao;

import com.dao.entities.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements IAdminDao {

    private Connection conn;

    private final String TABLE_NAME = "admin";

    public AdminDaoImpl() {
        conn = DbConnection.getConnection();
    }

    @Override
    public Admin findByUsername(String username) {
        return null;
    }

    @Override
    public String getSalt(String username) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT salt FROM " + TABLE_NAME + " WHERE username = ?");
            ps.setString(1, username);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                return result.getString("salt");
            }
        } catch (SQLException e) {
            return null;
        }

        return null;
    }

    @Override
    public Admin findByUsernamePassword(String username, String saltHashPassword) {

        Admin admin = null;

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id,username FROM " + TABLE_NAME + " WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, saltHashPassword);

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                admin = new Admin();

                admin.setId(result.getLong("id"));
                admin.setUsername(result.getString("username"));
            }
        } catch (SQLException e) {
            return null;
        }

        return admin;
    }
}