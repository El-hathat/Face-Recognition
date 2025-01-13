package com.dao;


import com.dao.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements IUserDao {


    private Connection conn;
    private PreparedStatement ps;
    private Statement st;

    private final String TABLE_NAME = "users";

    public UserDaoImpl() {
        conn = DbConnection.getConnection();
    }

    @Override
    public User findOne(Long id) {
        return null;
    }

    public List<User> findAll() {
        try {
            st = conn.createStatement();
            String query = "SELECT * FROM users";
            ResultSet set = st.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (set.next()) {
                User user = new User(set.getLong("id"), set.getString("name"), set.getString("email"), set.getString("tel"), set.getString("passcode"), set.getString("imagepath"), set.getBoolean("active"));
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(User entity) {

        try {
            ps = conn.prepareStatement("INSERT INTO " + TABLE_NAME + "(name, email, tel, passCode, imagePath, active) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getTel());
            ps.setString(4, entity.getPassCode());
            ps.setString(5, entity.getImagePath());
            ps.setBoolean(6, entity.isActive());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public boolean update(User entity) {
        try {
            ps = conn.prepareStatement("UPDATE " + TABLE_NAME + " SET name = ?, email = ?, tel = ?, passCode = ?, imagePath = ?, active = ? WHERE id = ?");
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getTel());
            ps.setString(4, entity.getPassCode());
            ps.setString(5, entity.getImagePath());
            ps.setBoolean(6, entity.isActive());
            ps.setLong(7, entity.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(User entity) {
        return deleteById(entity.getId());
    }

    @Override
    public boolean deleteById(Long entityId) {
        try {
            ps = conn.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
            ps.setLong(1, entityId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}