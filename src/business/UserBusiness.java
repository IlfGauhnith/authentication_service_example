package business;

import data.UserDAO;
import model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserBusiness {
    private UserDAO userDAO;

    public UserBusiness() {
        userDAO = UserDAO.getInstance();
    }

    public void create(String userName, String password) throws SQLIntegrityConstraintViolationException {
        //Checking username and password conventions
        if(userName.length() < 8) {
            throw new IllegalArgumentException("username too short");
        } else if(password.length() < 8) {
            throw new IllegalArgumentException("password too short");
        } else if(userDAO.exists(userName)) {
            throw new SQLIntegrityConstraintViolationException("Primary key constraint violated");
        }
        //salt generation
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);

        //password hashing
        byte[] hashedPassword = this.saltAndHash(password, salt);

        User user = new User(userName, salt, hashedPassword);
        userDAO.insert(user);
    }

    public boolean login(String userName, String password) throws IllegalArgumentException {
        //Check if credentials are valid.
        //Set user as active in db if it is valid and return true
        //Case not exists return false.

        //Checking username and password conventions
        if(userName.length() < 8) {
            throw new IllegalArgumentException("username too short");
        } else if(password.length() < 8) {
            throw new IllegalArgumentException("password too short");
        }

        //getSalt from db
        byte[] salt = userDAO.getSalt(userName);
        //password hashing
        byte[] hashedPassword = this.saltAndHash(password, salt);

        User user = new User(userName, salt, hashedPassword);
        boolean logon = userDAO.exists(user);

        if (logon) userDAO.login(user);

        return logon;
    }

    private byte[] saltAndHash(String password, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
