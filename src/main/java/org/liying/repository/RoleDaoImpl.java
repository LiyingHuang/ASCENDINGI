package org.liying.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.liying.model.Role;
import org.liying.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    @Override
    public Role getRoleByName(String name) {
        String hql = "FROM Role as r where lower(r.name) = :Name";  // Role is the java class name
        Session session = sessionFactory.openSession();
        try{
            Query<Role> query = session.createQuery(hql);
            query.setParameter("Name", name.toLowerCase());
            Role result = query.uniqueResult(); //??
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve Role by Name", e);
            session.close();
            return null;
        }
    }

    @Override
    public List<Role> findAllRoles() {
        String hql = "FROM Role";
        Session session = sessionFactory.openSession();
        List<Role> result =  new ArrayList<>();
        try{
            Query<Role> query = session.createQuery(hql);
            result = query.list ();
            session.close();
        }catch (Exception e){
            logger.error("session close exception try again");
        }
        return result;
    }

    @Override
    public Role save(Role r) {
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            //session.persist(user);
            session.save(r);
            transaction.commit();
            session.close();
            return r;
        }catch (Exception e){
            if (transaction != null) transaction.rollback();
            logger.error("Failure to create Role record");
            session.close();
            return null;
        }
    }

    @Override
    public Role findById(Long Id) {
        String hql = "FROM Role r where r.id = :Id";
        Session session = sessionFactory.openSession();
        try{
            Query<Role> query = session.createQuery(hql);
            query.setParameter("Id", Id);
            Role result = query.uniqueResult();
            session.close();
            return result;
        } catch (HibernateException e) {
            logger.error("Failure to retrieve Role By Id", e);
            session.close();
            return null;
        }
    }

    @Override
    public boolean delete(Role r) {
        String hql = "DELETE Role r where r.id = :Id";
        int deleteCount = 0;
        Transaction transaction = null;
        Session session = sessionFactory.openSession();
        try{
            transaction = session.beginTransaction();
            Query<Role> query = session.createQuery(hql);
            query.setParameter("Id",r.getId());
            deleteCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deleteCount >=1 ? true : false;
        }
        catch(HibernateException e){
            if(transaction != null) transaction.rollback();
            session.close();
            logger.error("Unable to delete Role record",e);
        }
        return false;
    }
}
