package model;


import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class User {
    @Id
    @Column(name = "usuario", nullable = false, length = 50)
    private String userName;
    @Column(name = "senha", nullable = false, length = 32)
    private byte[] hashedPassword;
    @Column(name = "salt", nullable = false, length = 32)
    private byte[] salt;
    @Column(name = "ativo", nullable = false)
    private boolean ativo;
    @Column(name = "ultimo_acesso")
    private LocalDateTime ultimoAcesso;

    public User(String userName, byte[] salt, byte[] hashedPassword) {
        this.userName = userName;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }

    public User() {
    }

    @PrePersist
    protected void onCreate() {
        ultimoAcesso = LocalDateTime.now();
        ativo = false;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }
}

