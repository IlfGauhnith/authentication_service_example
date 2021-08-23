package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {

    private static String url = "jdbc:postgresql://localhost:5432/login";

    private final static  String user = "root";
    private final static String password = "root";

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
}
