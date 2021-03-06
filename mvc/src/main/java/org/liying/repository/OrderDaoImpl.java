package org.liying.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.liying.model.Consumer;
import org.liying.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.liying.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao{

    @Autowired private SessionFactory sessionFactory;

    private Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public Order save(Order order) {
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            session.close();
            return order;
        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            logger.error("Failure to insert record");
            session.close();
            return null;
        }
    }

    @Override
    public List<Order> getOrders() {
        String hql = "FROM Order";
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        List<Order> result = new ArrayList<>();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(hql);
            result = query.list();
            transaction.commit();
            session.close();
        }catch (HibernateException e){
            if(transaction != null){
                transaction.rollback();
            }
            logger.error("Session close exception try again...", e);
            session.close();
        }
        return result;
    }

    @Override
    public Order getBy(Long id) {
        String hql = "FROM Order od WHERE od.id = :Id";
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query<Order> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Order result = query.uniqueResult();
            transaction.commit();
            session.close();
            return result;
        }catch (HibernateException e){
            if(transaction != null){
                transaction.rollback();
            }
            logger.error("Failure to retrieve data record", e);
            session.close();
            return null;
        }
    }

    @Override
    public boolean delete(Order order) {
        String hql = "DELETE Order as od where od.id = :Id";
        int deleteCount = 0;
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        transaction = session.beginTransaction();
        try{
            Query<Order> query = session.createQuery(hql);
            query.setParameter("Id",order.getId());
            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deleteCount >=1 ? true : false;
        }catch (HibernateException e){
            if(transaction != null){
                transaction.rollback();
            }
            session.close();
            logger.error("Unable to delete record", e);
        }
        return false;
    }

    // getOrderEagerBy(long id)
    // getBy(Consumer c) order -> consumer (1 -> 1)
    @Override
    public Order getBy(Consumer c) {
        // ???
        String hql = "FROM Order od LEFT JOIN FETCH od.consumer c WHERE c.id = :Id";
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Query<Order> query = session.createQuery(hql);
            query.setParameter("Id", c.getId());
            Order result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("Failure to retrieve data record", e);
            session.close();
            return null;
        }
    }

    @Override
    public Order update(Order order) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            transaction.commit();
            session.close();
            return order;
        }catch(Exception e){
            if(transaction != null) transaction.rollback();
            logger.error("Failure to update data.");
            session.close();
            return null;
        }
    }

    @Override
    public boolean delete(String orderName) {
        return false;
    }

    @Override
    public List<Order> getOrdersEager() {
        return null;
    }

    @Override
    public Order getOrderByName(String orderName) {
        return null;
    }

    @Override
    public Order getPlatformsAndOrdersBy(String orderName) {
        return null;
    }

    @Override
    public List<Object[]> getShoppingPlatformAndConsumersAndOrders(String orderName) {
        return null;
    }
}
