package data;

import model.User;
import persistence.HibernateUtil;

import javax.persistence.EntityManager;
import java.sql.*;
import java.time.LocalDateTime;

public class UserDAO {

    private EntityManager entityManager;
    private static UserDAO instance;

    private UserDAO() {
        this.entityManager = HibernateUtil.getInstance().getEntityManager();
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

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public boolean exists(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException();
        }
        entityManager.getTransaction().begin();
        User ret = entityManager.find(User.class, username);
        entityManager.getTransaction().commit();

        if (ret != null) return true;
        return false;
    }

    public boolean exists(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        entityManager.getTransaction().begin();
        User ret = entityManager.find(User.class, user.getUserName());
        entityManager.getTransaction().commit();

        if (ret != null) return true;
        return false;
    }

    public byte[] getSalt(String userName) {
        entityManager.getTransaction().begin();
        User ret = entityManager.find(User.class, userName);
        entityManager.getTransaction().commit();


        return ret.getSalt();
    }

    public void login(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        user = entityManager.find(User.class, user.getUserName());

        entityManager.getTransaction().begin();
        user.setAtivo(true);
        user.setUltimoAcesso(LocalDateTime.now());
        entityManager.getTransaction().commit();
    }
}
