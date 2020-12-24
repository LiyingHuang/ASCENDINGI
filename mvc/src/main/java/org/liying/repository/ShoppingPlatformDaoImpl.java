package org.liying.repository;

import org.hibernate.*;
import org.hibernate.query.Query;
import org.liying.model.ShoppingPlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.liying.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;


@Repository // 使用在dao（data access object）层上用于实例化bean  repository仓库：因为dao就是做数据存储，持久化
// Store in Storage: ShoppingPlatformDao sp = new ShoppingPlatformDaoImpl();
public class ShoppingPlatformDaoImpl implements ShoppingPlatformDao {

    @Autowired  // 没有用@qualifier声明注入的bean的名字，这时就是通过数据类型从spring容器中进行匹配注入的
    // 用@qualifier（name=" "）声明了注入的bean的名字：按照id值从容器中进行匹配，要结合autowired进行使用
    // @Resourse （name=" "）
    private SessionFactory sessionFactory;
    private Logger logger = LoggerFactory.getLogger(getClass());

    // session.save 保存数据功能
    @Override
    public ShoppingPlatform save(ShoppingPlatform shoppingPlatform) {
        Transaction transaction = null;
        //Session session = HibernateUtil.getSessionFactory().openSession();
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // 得到session对象，该对象sesssion已经实现了基本的sql增删改查语句
        //
        Session session = sessionFactory.openSession();
        try{
            // 事务管理: 开启事务（通过session对象）
            transaction = session.beginTransaction();
            // 保存一个对象/数据
            session.save(shoppingPlatform);
            // 提交事务
            transaction.commit();
            // 释放资源
            session.close();
            return shoppingPlatform;
        }catch (Exception e){
            //
            if (transaction != null) transaction.rollback();
            logger.error("Failure to insert record");
            session.close();
            return null;
        }
    }
    //  session.createQuery(hql) 取到所有的ShoppingPlatform
    @Override
    public List<ShoppingPlatform> getShoppingPlatforms() {
        String hql = "FROM ShoppingPlatform";
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
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
    // 根据主键查询数据 (查询其实不需要事务管理，增删改需要)
    // 方法1：session.createQuery(hql)
    // 方法2：session.get(ShoppingPlatform.class, 2);
    @Override
    public ShoppingPlatform getBy(Long id) {
        // String hql = "FROM ShoppingPlatform sp where sp.id = :Id";
        String hql = "FROM ShoppingPlatform sp where sp.id = ?";

        //String hql = "FROM ShoppingPlatform sp JOIN FETCH sp.consumers where sp.id = :Id";
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        try{
            Query<ShoppingPlatform> query = s.createQuery(hql);
            // String hql = "FROM ShoppingPlatform sp where sp.id = :Id";
            // query.setParameter("Id", id);
            query.setParameter(0, id);
            ShoppingPlatform result = query.uniqueResult();
            s.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve data record", e);
            s.close();
            return null;
        }
    }
    // delete删除功能
    // sesssion.delete(对象) session自带的删除方法传入的是一个对象
    // 也可实现相同的功能用下面的hql语句通过主键来删除的方法
    @Override
    public boolean delete(ShoppingPlatform shop){
        // :Id placeholder
        String hql = "DELETE ShoppingPlatform as shop where shop.id = :Id";
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            // throws
            Query<ShoppingPlatform> query = session.createQuery(hql);
            query.setParameter("Id",shop.getId());
            // no throws
            // jdbc中调用的
            deleteCount = query.executeUpdate(); // 返回的是table里受上面hql影响的行数
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
        //SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session s = sessionFactory.openSession();
        try{
            Query<ShoppingPlatform> query = s.createQuery(hql);
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
        Transaction transaction = null;
        // SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(shoppingPlatform);
            transaction.commit();
            return  shoppingPlatform;
        }catch(Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record", e.getMessage());
            return null;
        }
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
