import java.sql.Connection;

public class Main {

  public static void main(String[] args) {
    SqlServerConnector sqlConnector = new SqlServerConnector();
    Connection connection = sqlConnector.connectToSqlServer();
    String readPlayers = "SELECT * FROM game_data.players;";
    String createPlayer =
        "INSERT INTO game_data.players (username, player_level, class_id) SELECT ?, 1, class_id"
            + " FROM game_data.classes WHERE class_name=?;";
    String updatePlayer = "UPDATE game_data.players SET player_level += ? WHERE username = ?;";
    String deletePlayer = "DELETE FROM game_data.players WHERE username = ?;";
    String detailedPlayerInfo =
        "SELECT c.class_name, COUNT(p.player_id) AS total_players, AVG(p.player_level) AS"
            + " average_level FROM game_data.classes c JOIN game_data.players p ON c.class_id ="
            + " p.class_id GROUP BY c.class_name"; // Average level of each class
    String username = "Example"; // temporary info get this from user
    String player_class = "Rogue"; // temporary info get this from user
    int level_up_by = 2; // temporary info get this from user
    sqlConnector.createNewPlayer(
        createPlayer, connection, username, player_class); // Should be activated by the user
    sqlConnector.getPlayers(readPlayers, connection);
    sqlConnector.levelUp(updatePlayer, connection, level_up_by, username);
    sqlConnector.getPlayers(readPlayers, connection);
    sqlConnector.deletePlayer(deletePlayer, connection, username);
    sqlConnector.getPlayers(readPlayers, connection);
    sqlConnector.getAverageLevels(detailedPlayerInfo, connection);
    sqlConnector.endConnection(connection);
  }
}
