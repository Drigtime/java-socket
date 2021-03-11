package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    final String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection connexion;

//    "jdbc:mysql://localhost:3306/java"
//    "root"
//    "root"

    public Database(String url, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        connexion = DriverManager.getConnection(url, user, password);
    }

    public void createTable(String table) throws SQLException {
        Statement statement = connexion.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS " + table + " ( `id` INT NOT NULL AUTO_INCREMENT , `message` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`))";
        int e = statement.executeUpdate(sql);
        statement.close();
    }

    public void insertMessage(String table, String message) throws SQLException {
        Statement statement = connexion.createStatement();
        String sql = "INSERT INTO `" + table + "` (`message`) VALUES ('" + message + "')";
        int e = statement.executeUpdate(sql);
        statement.close();
    }
}
