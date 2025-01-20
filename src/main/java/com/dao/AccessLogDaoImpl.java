package com.dao;

import com.dao.entities.AccessLog;
import com.dao.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccessLogDaoImpl implements IAccessLogDao {

    private Connection conn;
    private PreparedStatement ps;
    private Statement st;

    private final String TABLE_NAME = "access_log";

    public AccessLogDaoImpl() {
        conn = DbConnection.getConnection();
    }


    @Override
    public AccessLog findOne(Long id) {
        AccessLog accessLog = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM users JOIN "+TABLE_NAME+" ON users.id = access_log.user_id WHERE access_log.id = ?");
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                User user = new User(result.getLong("id"), result.getString("name"), result.getString("email"), result.getString("tel"), result.getString("passcode"), result.getString("imagepath"), result.getBoolean("active"));
                accessLog = new AccessLog(result.getLong("log_id"), user, result.getTimestamp("access_time"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessLog;
    }

    @Override
    public List<AccessLog> findAll() {
        List<AccessLog> accessLogs = null;

        try {
            st = conn.createStatement();
            String query = "SELECT * FROM users JOIN "+TABLE_NAME+" ON users.id = access_log.user_id";
            ResultSet result = st.executeQuery(query);
            accessLogs = new ArrayList<>();
            while (result.next()) {
                User user = new User(result.getLong("id"), result.getString("name"), result.getString("email"), result.getString("tel"), result.getString("passcode"), result.getString("imagepath"), result.getBoolean("active"));
                AccessLog accessLog = new AccessLog(result.getLong("log_id"), user, result.getTimestamp("access_time"));
                accessLogs.add(accessLog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return accessLogs;
    }

    @Override
    public boolean save(AccessLog entity) {
        try {
            ps = conn.prepareStatement("INSERT INTO " + TABLE_NAME + " (user_id, access_time) VALUES (?, ?)");
            ps.setLong(1, entity.getUser().getId());
            ps.setTimestamp(2, entity.getAccessTime());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(AccessLog entity) {
        return false;
    }

    @Override
    public boolean delete(AccessLog entity) {
        return false;
    }

    @Override
    public boolean deleteById(Long entityId) {
        return false;
    }

    @Override
    public int count() {

        try {
            st = conn.createStatement();
            String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
            ResultSet set = st.executeQuery(query);
            if (set.next()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}