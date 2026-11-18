# CSE3153Project

## To create Database

1. Run the `FinalProject.sql` file

To connect to the Database

1. Run the `CreateUser.sql` file.

2. In your SSMS right click on the server name under `Object Explorer`.

3. When the popup opens up look down until you get to `Properties`

4. A menu will open up, now go to `Security` (Should be the 4th option)

5. Look under `Server Authentication` and change it to `SQL Server and Windows Authentication mode`

## To run the program

1. Install `Maven` if you have not already.

2. Run `mvn clean compile exec:java -D maven.compiler.source=21 -D maven.compiler.target=21 -D exec.mainClass="Main"` in this directory.
