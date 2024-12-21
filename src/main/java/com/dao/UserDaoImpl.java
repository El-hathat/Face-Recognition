package com.dao;


import com.dao.entities.User;

import java.sql.*;
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
            List<User> users = null;
            while (set.next()) {
                User user = new User(set.getLong("id"), set.getString("name"), set.getString("email"), set.getString("tel"), set.getString("passcode"), set.getString("imagepath"), set.getBoolean("isactive"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean save(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public boolean deleteById(Long entityId) {
        return false;
    }
}