import java.sql.Connection;

public class Main {

  public static void main(String[] args) {
    SqlServerConnector sqlConnector = new SqlServerConnector();
    Connection connection = sqlConnector.connectToSqlServer();
    String readPlayers = "SELECT * FROM game_data.players;";
    sqlConnector.query(readPlayers, connection);
    sqlConnector.endConnection(connection);
  }
}
