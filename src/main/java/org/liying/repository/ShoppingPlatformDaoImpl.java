package org.liying.repository;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.liying.model.ShoppingPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class ShoppingPlatformDaoImpl implements ShoppingPlatformDao {

    private Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    // create
    @Override
    public ShoppingPlatform save(ShoppingPlatform shoppingPlatform) {
        Transaction transaction = null;
        //Session session = HibernateUtil.getSessionFactory().openSession();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            session.save(shoppingPlatform);
            transaction.commit();
            session.close();
            return shoppingPlatform;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("Failure to insert record");
            session.close();
            return null;
        }
    }
    // retrieve
    @Override
    public List<ShoppingPlatform> getShoppingPlatforms() {
        String hql = "FROM ShoppingPlatform";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        List<ShoppingPlatform> result = new ArrayList<>();
        try {
            Query<ShoppingPlatform> query = s.createQuery(hql);
            result = query.list();
            s.close();
        }catch(HibernateException e){
            logger.error("session close exception try again...", e);
            s.close();
        }
        return result;
    }
    // retrieve
    @Override
    public ShoppingPlatform getBy(Long id) {
        String hql = "FROM ShoppingPlatform sp where sp.id = :Id";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        try{
            Query<ShoppingPlatform> query = s.createQuery(hql);
            query.setParameter("Id", id);
            ShoppingPlatform result = query.uniqueResult();
            s.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve data record", e);
            s.close();
            return null;
        }
    }
    // delete
    @Override
    public boolean delete(ShoppingPlatform shop){
        // :Id placeholder
        String hql = "DELETE ShoppingPlatform as shop where shop.id = :Id";
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            transaction = session.beginTransaction();
            // throws
            Query<ShoppingPlatform>query = session.createQuery(hql);
            query.setParameter("Id",shop.getId());
            // no throws
            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deleteCount >=1 ? true : false;
        }
        catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",e);
        }
        return false;
    }

    @Override
    public ShoppingPlatform getShoppingPlatformsEagerBy(Long id) {
        String hql = "FROM ShoppingPlatform sp LEFT JOIN FETCH sp.consumers where sp.id = :Id";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        try{
            Query<ShoppingPlatform> query = s.createQuery(hql); // before set?
            query.setParameter("Id", id);
            ShoppingPlatform result = query.uniqueResult(); //??
            s.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve data record", e);
            s.close();
            return null;
        }
    }

    @Override
    public List<ShoppingPlatform> getShoppingPlatformsEager() { return null; }

    @Override
    public ShoppingPlatform update(ShoppingPlatform shoppingPlatform) {
        return null;
    }

    @Override
    public boolean delete(String platformName) {
        return false;
    }


    @Override
    public ShoppingPlatform getShoppingPlatformByName(String platformName) {
        return null;
    }

    @Override
    public ShoppingPlatform getShoppingPlatformAndConsumersBy(String platformName) {
        return null;
    }

    @Override
    public List<Object[]> getShoppingPlatformAndConsumersAndOrders(String platformName) {
        return null;
    }
}