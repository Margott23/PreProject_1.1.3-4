package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    final static String CREATE_NEW_TABLE = "CREATE TABLE IF NOT EXISTS users (id int primary key auto_increment, name varchar(45), lastName varchar(45), age int)";
    final static String DROP_TABLE = "DROP TABLE IF EXISTS users";
    final static String CLEAN_USER_TABLE = "DELETE FROM users";

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_NEW_TABLE);
            query.executeUpdate();
            System.out.println("Table created successful");
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(DROP_TABLE);
            query.executeUpdate();
            System.out.println("Table drop successful");
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.printf("User %s successful added\n", name);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User removed successful");
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
        }
        System.out.println("Get all users successful");
        System.out.println(users);
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getInstance().getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery(CLEAN_USER_TABLE);
            query.executeUpdate();
            System.out.println("Table clean successful");
            session.getTransaction().commit();
        }
    }
}
