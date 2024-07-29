package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getInstance().getSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id int primary key auto_increment, name varchar(45), lastName varchar(45), age int)");
            query.executeUpdate();
            System.out.println("Table created successful");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("UserDaoHibernateImpl exception: method createUsersTable");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getInstance().getSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users");
            query.executeUpdate();
            System.out.println("Table drop successful");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("UserDaoHibernateImpl exception: method dropUsersTable");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getInstance().getSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.printf("User %s successful added\n", name);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("Rollback done");
            System.err.println("UserDaoHibernateImpl exception: method saveUser");
        }

        try {
            session.close();
        } catch (HibernateException e) {
            System.err.println("Session close failed");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getInstance().getSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User removed successful");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("UserDaoHibernateImpl exception: method removeUserById");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getInstance().getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM User", User.class);
            users = query.getResultList();
            System.out.println("Get all users successful");
            System.out.println(users);
            return users;
        } catch (HibernateException e) {
            System.err.println("UserDaoHibernateImpl exception: method getAllUsers");
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getInstance().getSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM users");
            query.executeUpdate();
            System.out.println("Table clean successful");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.err.println("UserDaoHibernateImpl exception: method cleanUsersTable");
        }
    }
}
