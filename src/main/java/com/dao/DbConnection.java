package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static Connection conn = null;

    static {
        try {
            Class.forName(ConfigDao.DB_DRIVER);
            conn = DriverManager.getConnection(
                    ConfigDao.DB_URL + ConfigDao.DB_NAME,
                        ConfigDao.DB_USERNAME,
                        ConfigDao.DB_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return conn;
    }
}
