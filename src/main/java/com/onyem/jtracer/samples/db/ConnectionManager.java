package com.onyem.jtracer.samples.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionManager {

  private final String url;
  private final String username;
  private final String password;

  private final BasicDataSource dataSource;

  public ConnectionManager(String driver, String url, String username,
      String password) {

    this.url = url;
    this.username = username;
    this.password = password;

    // Load driver for normal connections
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Configure datasource
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(driver);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    this.dataSource = dataSource;
  }

  public synchronized Connection getNormalConnection() throws SQLException {
    Connection connection = DriverManager
        .getConnection(url, username, password);
    return connection;
  }

  public synchronized Connection getPooledConnection() throws SQLException {
    return dataSource.getConnection();
  }

  public synchronized void closeConnection(Connection connection)
      throws SQLException {
    connection.close();
  }
}
