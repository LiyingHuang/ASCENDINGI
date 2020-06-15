package org.liying.repository;
import org.hibernate.*;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.liying.model.Consumer;
import org.liying.model.Order;
import org.liying.model.ShoppingPlatform;
import sun.security.ssl.HandshakeInStream;
import util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
public class ConsumerDaoImpl implements  ConsumerDao {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    //create
    public Consumer save(Consumer consumer) {
        Transaction transaction = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            transaction =session.beginTransaction();
            session.save(consumer);
            transaction.commit();
            session.close();
            return consumer;
        }catch (Exception e){
            if(transaction != null) transaction.rollback();
            logger.error("Failure to insert record");
            session.close();
            return null;
        }
    }
    @Override
    //retrieve
    public List<Consumer> getConsumers() {
        String hql = "FROM Consumer";
        SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
        Session S = sessionFactory.openSession();
        List<Consumer> result = new ArrayList<>();
        try{
            Query<Consumer>query = S.createQuery(hql);
            result = query.list();
            S.close();
        }catch (HibernateException e){
            logger.error("session close exception try again");
            S.close();
        }
        return result;
    }
    @Override
    //update
    public Consumer getBy(Long id) {
        String hql =" FROM Consumer c where c.id =Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Query <Consumer> query =session.createQuery(hql);
            query.setParameter("Id" , id);
            Consumer result =query.uniqueResult();
            session.close();
            return  result;
        }catch(HibernateException e){
            logger.error("failure to retireve data record", e);
            session.close();
            return null;
        }
    }
    @Override
    // delete
    public boolean delete(Consumer consumer) {
        String hql ="DELETE Consumer as con where con.id=:Id";
        int deletedCount = 0;
        Transaction transaction = null;
        Session session =HibernateUtil.getSessionFactory().openSession();
        try{
            transaction = session.beginTransaction();
            Query<Consumer>query = session.createQuery(hql);
            query.setParameter("Id",consumer.getId());
            deletedCount=query.executeUpdate();
            transaction.commit();
            session.close();
            return  deletedCount >= 1? true:false;
        }catch (HibernateException e){
            if (transaction != null)transaction.rollback();
            session.close();
            logger.error("unanle to delete record" , e);
        }
        return  false;
    }
    @Override
    public Consumer getConsumerEagerBy(Long id) {
        String hql = "FROM Consumer cons LEFT JOIN FETCH cons.orders WHERE cons.id = :Id";
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            Query<Consumer> query = session.createQuery(hql);
            query.setParameter("Id", id);
            Consumer result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("Failure to retrieve data record", e);
            session.close();
            return null;
        }
    }


    @Override
    public Consumer update(Consumer consumer) {
        return null;
    }
    @Override
    public boolean delete(String consumerName) {
        return false;
    }
    @Override
    public List<Consumer> getConsumersEager() {
        return null;
    }
    @Override
    public Consumer getConsumerByName(String platformName) {
        return null;
    }
    @Override
    public Consumer getConsumerAndOrdersBy(String platformName) {
        return null;
    }
    @Override
    public List<Object[]> getShoppingPlatformAndConsumersAndOrders(String platformName) {
        return null;
    }
}