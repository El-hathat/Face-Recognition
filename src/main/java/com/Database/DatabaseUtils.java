package com.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
  private static final String URL = "jdbc:mysql://localhost:3306/access_control";
  private static final String USER = "root"; // Replace with your MySQL username
  private static final String PASSWORD = ""; // Replace with your password

  public static Connection connect() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
