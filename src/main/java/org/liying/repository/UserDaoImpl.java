package org.liying.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.liying.model.ShoppingPlatform;
import org.liying.model.User;
import org.liying.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private SessionFactory sessionFactory;

    private Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public User save(User user) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            //session.persist(user);
            session.save(user);
            transaction.commit();
            session.close();
            return user;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("Failure to create User record");
            session.close();
            return null;
        }
//        if(user != null) logger.debug(String.format("The User %s was insert"), user.getFirstName());
//        return user;
    }

    @Override
    public User findById(Long Id) {
        String hql = "FROM User user where user.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", Id);
            User result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve User By Id", e);
            session.close();
            return null;
        }
    }

    @Override
    public User getUserByEmail(String eamil) {
        String hql = "FROM User as u where lower(u.email) = :Email";
        Session session = sessionFactory.openSession();
        try{
            Query<User> query = session.createQuery(hql);
            query.setParameter("Email", eamil.toLowerCase());
            User result = query.uniqueResult(); //??
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve User by Email", e);
            session.close();
            return null;
        }
    }

    @Override
    public User getUserByCredentials(String email, String xx) {
        return null;
    }

    @Override
    public boolean delete(User u) {
        String hql = "DELETE User user where user.id = :Id";
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id",u.getId());
            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deleteCount >=1 ? true : false;
        }
        catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            session.close();
            logger.error("Unable to delete User record",e);
        }
        return false;
    }

    @Override
    public List<User> getAllUsers(){
        String hql = "FROM User";
        Session session = sessionFactory.openSession();
        List<User> result =  new ArrayList<>();
        try{
            Query<User> query = session.createQuery(hql);
            result = query.list ();
            session.close();
        }catch (Exception e){
            logger.error("session close exception try again");
            session.close();
        }
        return result;
    }
}
