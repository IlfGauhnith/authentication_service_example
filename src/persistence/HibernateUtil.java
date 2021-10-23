package persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static HibernateUtil instance;
    private EntityManagerFactory entityManagerFactory;

    private HibernateUtil() {
        entityManagerFactory = Persistence.createEntityManagerFactory("login-PU");
    }

    public static HibernateUtil getInstance() {
        if (instance == null) {
            synchronized (HibernateUtil.class) {
                if (instance == null) {
                    instance = new HibernateUtil();
                }
            }
        }
        return instance;
    }

    public EntityManager getEntityManager() {return this.entityManagerFactory.createEntityManager();}
}
