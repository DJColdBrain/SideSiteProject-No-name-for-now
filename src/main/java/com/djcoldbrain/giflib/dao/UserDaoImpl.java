package com.djcoldbrain.giflib.dao;

import com.djcoldbrain.giflib.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
        Query<User> q  = session.createQuery(query);
        List<User> users = q.getResultList();
        session.close();
        return users;
    }

    @Override
    public User findByUsername(String q) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root).where(builder.equal(root.get("username"), q));
        Query<User> query1  = session.createQuery(query);
        User user = query1.getSingleResult();
        session.close();
        return user;
    }

    @Override
    public User findById(long id) {

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        Query<User> q  = session.createQuery(query);
        User user = q.getSingleResult();
        session.close();
        return user;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
