package com.onyem.jtracer.samples.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

  public static void main(String[] args) throws SQLException {
    Main main = new Main();
    main.process();
  }

  public void process() throws SQLException {
    ConnectionManager connectionManager = create();

    createData(connectionManager);

    // Run query with normal connection
    {
      Connection normalConnection = connectionManager.getNormalConnection();
      executeQuery("Normal", connectionManager, normalConnection);
    }

    // Run query with pooled connection
    for (int i = 0; i < 2; i++) {
      Connection pooledConnection = connectionManager.getPooledConnection();
      executeQuery("Pool(" + i + ")", connectionManager, pooledConnection);
    }
  }

  private void createData(ConnectionManager connectionManager)
      throws SQLException {
    Connection connection = connectionManager.getNormalConnection();
    try {
      Statement statement = connection.createStatement();
      statement
          .execute("CREATE TABLE FOO (NAME VARCHAR(10), DESCRIPTION VARCHAR(50))");

      statement.execute("INSERT INTO FOO VALUES('A', 'a')");
      statement.execute("INSERT INTO FOO VALUES('B', 'b')");
    } finally {
      connectionManager.closeConnection(connection);
    }
  }

  private void executeQuery(String prefix, ConnectionManager connectionManager,
      Connection connection) throws SQLException {
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM FOO");
      while (resultSet.next()) {
        String result = resultSet.getString(1) + "->" + resultSet.getString(2);
        System.out.println(prefix + ":" + result);
      }
    } finally {
      connectionManager.closeConnection(connection);
    }
  }

  // Utility methods

  private ConnectionManager create() {
    String driver = "org.h2.Driver";
    String url = "jdbc:h2:" + getTempPath() + "/tmp";
    String username = "sa";
    String password = "";
    return new ConnectionManager(driver, url, username, password);
  }

  private String getTempPath() {
    File tmp;
    try {
      tmp = File.createTempFile("onyem", null);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String path = tmp.getAbsolutePath();
    tmp.delete();
    return path;
  }

}
