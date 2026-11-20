import java.sql.Connection;
import java.util.*;

public class Main {

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    SqlServerConnector sqlConnector = new SqlServerConnector();
    Connection connection = sqlConnector.connectToSqlServer();

    if (connection == null){
      System.out.println("Database Failed.");
      System.out.println("Unable to Proceed.");
      input.close();
      return;
    }

    String readPlayers = "SELECT * FROM game_data.players;";
    String createPlayer =
        "INSERT INTO game_data.players (username, player_level, class_id) SELECT ?, 1, class_id"
            + " FROM game_data.classes WHERE class_name=?;";
    String updatePlayer = "UPDATE game_data.players SET player_level += ? WHERE username = ?;";
    String deletePlayer = "DELETE FROM game_data.players WHERE username = ?;";
    String detailedPlayerInfo =
        "SELECT c.class_name, COUNT(p.player_id) AS total_players, AVG(p.player_level) AS"
            + " average_level FROM game_data.classes c LEFT JOIN game_data.players p ON c.class_id ="
            + " p.class_id GROUP BY c.class_name"; // Average level of each class


    int option = 0;

    try{
      while(option != 6){
        System.out.println("");
        System.out.println("RPG GAME");
        System.out.println("");
        System.out.println("1. View All Players");
        System.out.println("2. Create New Player");
        System.out.println("3. Level Up Character");
        System.out.println("4. Delete Player");
        System.out.println("5. View Stats");
        System.out.println("6. Quit");
        System.out.println("");
        System.out.println("Enter your choice:");

        if (!input.hasNextInt()) {
          System.out.println("Please enter a valid number.");
          input.nextLine();
          continue;
        }
        option = input.nextInt();
        input.nextLine();

        switch(option){
          case 1:
            sqlConnector.getPlayers(readPlayers, connection);
            break;
          case 2:
            System.out.println("Enter New Player's Username:");
            String username = input.nextLine();

            System.out.println("Enter New Character Class: ");
            String player_class = input.nextLine();

            if (username.isEmpty() || player_class.isEmpty()) {
              System.out.println("Username nor class can be empty.");
              break;
            }

            sqlConnector.createNewPlayer(
                    createPlayer, connection, username, player_class);
            break;
          case 3:
            System.out.println("Enter Player Name: ");
            String level_user = input.nextLine();

            System.out.println("Level Up Amount: ");
            if(!input.hasNextInt()){
              System.out.println("Please enter a valid number.");
              input.nextLine();
              break;
            }
            int level_up_by = input.nextInt();
            input.nextLine();

            if(level_up_by <= 0){
              System.out.println("Level up amount must be positive");
              break;
            }
            sqlConnector.levelUp(updatePlayer, connection, level_up_by, level_user);
            break;
          case 4:
            System.out.println("Enter username to delete: ");
            String delete_user = input.nextLine();

            System.out.println("Do you want to delete " + delete_user + "? (y/n");

            String confirm = input.nextLine();

            if(confirm.equalsIgnoreCase("y")){
              sqlConnector.deletePlayer(deletePlayer, connection, delete_user);
            } else {
              System.out.println("Canceled.");
            }
            break;
          case 5:
            sqlConnector.getAverageLevels(detailedPlayerInfo, connection);
            break;
          case 6:
            System.out.println("Exiting RPG...");
            break;
          default:
            System.out.println("Invalid! Try Again.");

        }
      }
    }finally {
      sqlConnector.endConnection(connection);
      input.close();

    }
  }
}

