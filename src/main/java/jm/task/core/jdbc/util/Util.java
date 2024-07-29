package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {
//    private final SessionFactory sessionFactory;
    private static volatile Util instance;

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public Util() {
    }

    private SessionFactory openSessionFactory() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }

    public Session getSession() {
        return Util.getInstance().openSessionFactory().openSession();
    }
}