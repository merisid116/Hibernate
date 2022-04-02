package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory = Util.getConnection();
    private String SQL_CREATE = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastName VARCHAR(255), age INT)";

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery(SQL_CREATE).executeUpdate();
            transaction.commit();
            System.out.println("table create");
        } catch (HibernateException e) {
            System.err.println("При создании таблицы пользователей произошло исключение\n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("drop table");
        } catch (HibernateException e) {
            System.err.println("При удалении таблицы пользователей произошло исключение\n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("INSERT INTO users (name , lastName , age) VALUES (?,?,?)")
                    .setParameter(1, name)
                    .setParameter(2, lastName)
                    .setParameter(3, age)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println("Во время сохранения пользователя произошло исключение\n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("DELETE FROM users WHERE id = " + id, User.class).executeUpdate();
            session.flush();
            transaction.commit();
            System.out.println("user delete");
        } catch (HibernateException e) {
            System.err.println("При удаление пользователя по id произошло исключение\n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = new ArrayList<>();
        try {
            users = session.createNativeQuery("SELECT * FROM users", User.class).getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println("При попытке достать всех пользователей из базы данных произошло исключение\n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createNativeQuery("TRUNCATE TABLE users;").executeUpdate();
            transaction.commit();
            System.out.println("table clear");
        } catch (HibernateException e) {
            System.err.println("При тестировании очистки таблицы пользователей произошло исключение\n" + e);
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }
    }
}
