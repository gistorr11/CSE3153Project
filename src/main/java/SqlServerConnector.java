import java.sql.*;

public class SqlServerConnector {

  private String connectionString =
      "jdbc:sqlserver://localhost;databaseName=RPG;encrypt=true;trustServerCertificate=true";

  private String user = "devuser";

  public Connection connectToSqlServer() {
    try {
      Connection connection = DriverManager.getConnection(connectionString, user, "");
      System.out.println("Connected to RPG Database");
      return connection;
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
      return null;
    }
  }

  public void query(String sql, Connection connection) {
    try {
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery(sql);
      while (result.next()) {
        System.out.println(result.getString("username"));
      }
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
    }
  }

  public void endConnection(Connection connection) {
    try {
      connection.close();
      System.out.println("Connection closed...");
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
    }
  }

  private void errorPrint(Exception e) {
    System.err.println("Error: " + e.getMessage());
  }
}
