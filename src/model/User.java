package model;

public class User {
    private int id;
    private String userName;
    private byte[] hashedPassword;
    private byte[] salt;

    public User(String userName, byte[] salt, byte[] hashedPassword) {
        this.userName = userName;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public byte[] getPassword() {
        return hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }
}
