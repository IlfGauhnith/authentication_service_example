package data;

import model.User;
import java.sql.*;

public class UserDAO {

    private Connection dbConnection;
    private String sqlQuery;
    private static UserDAO instance;

    private UserDAO() {
        this.dbConnection = DBConnectionFactory.getConnection();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (instance == null) {
                    instance = new UserDAO();
                }
            }
        }

        return instance;
    }

    public void insert(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuario nulo nao pode ser adicionado ao banco");
        }

        sqlQuery = "INSERT INTO usuario (usuario, senha, salt) ";
        sqlQuery += "VALUES (?, ?, ?);";

        try {
            PreparedStatement stmt = this.dbConnection.prepareStatement(sqlQuery);
            stmt.setString(1, user.getUserName());
            stmt.setBytes(2, user.getPassword());
            stmt.setBytes(3, user.getSalt());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean exists(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException();
        }

        sqlQuery = "SELECT 1 FROM usuario WHERE usuario = ? ;";

        try {
            PreparedStatement stmt = this.dbConnection.prepareStatement(sqlQuery);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean exists(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }

        sqlQuery = "SELECT 1 FROM usuario WHERE senha = ? " +
                "AND usuario = '" + user.getUserName() + "'";

        try {
            PreparedStatement stmt = this.dbConnection.prepareStatement(sqlQuery);
            stmt.setBytes(1, user.getPassword());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public byte[] getSalt(String userName) {
        sqlQuery = "SELECT salt FROM usuario WHERE usuario = '" + userName + "';";
        byte[] salt = new byte[32];

        try {
            Statement stmt = this.dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            if (rs.next()) salt = rs.getBytes("salt");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return salt;
    }

    public void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }

        sqlQuery = "UPDATE usuario SET ativo = ?, ultimo_acesso = ? " +
                "WHERE usuario = ? ;";

        try {
            PreparedStatement stmt = this.dbConnection.prepareStatement(sqlQuery);
            stmt.setBoolean(1, true);
            stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stmt.setString(3, user.getUserName());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
