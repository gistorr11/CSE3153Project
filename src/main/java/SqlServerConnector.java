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

  // Get Players
  public void getPlayers(String sql, Connection connection) {
    try {
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery(sql);
      System.out.println("\nPlayers in the RPG Game: \n");
      System.out.println("ID | Username | Level");
      while (result.next()) {
        System.out.println(
            result.getInt("player_id")
                + " | "
                + result.getString("username")
                + " | "
                + result.getInt("player_level"));
      }
      System.out.println();
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
    }
  }

  public void createNewPlayer(
      String sql, Connection connection, String username, String player_class) {
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, username);
      statement.setString(2, player_class);
      int result = statement.executeUpdate();
      if (result >= 1) {
        System.out.println("\nSuccessfully created player!!!");
      } else {
        System.out.println("\nFailed to create player");
      }
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
    }
  }

  public void levelUp(String sql, Connection connection, int level_up_by, String username) {
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, level_up_by);
      statement.setString(2, username);
      int result = statement.executeUpdate();
      if (result >= 1) {
        System.out.println("\nSuccessfully leveled up by " + level_up_by + "!!!");
      } else {
        System.out.println("\nFailed to level up player");
      }
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
    }
  }

  public void deletePlayer(String sql, Connection connection, String username) {
    try {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, username);
      int result = statement.executeUpdate();
      if (result >= 1) {
        System.out.println("\nSuccessfully deleted player!!!");
      } else {
        System.out.println("\nFailed to delete player");
      }
    } catch (SQLException sqlException) {
      errorPrint(sqlException);
    }
  }

  public void getAverageLevels(String sql, Connection connection) {
    try {
      Statement statement = connection.createStatement();
      ResultSet result = statement.executeQuery(sql);
      System.out.println("\nPlayers in the RPG Game: \n");
      System.out.println("Class | Total Player(s) | Avg Level");
      while (result.next()) {
        System.out.println(
            result.getString("class_name")
                + " | "
                + result.getInt("total_players")
                + " | "
                + result.getInt("average_level"));
      }
      System.out.println();
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
