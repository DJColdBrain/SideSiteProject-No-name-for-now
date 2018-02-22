package com.djcoldbrain.giflib.dao;

import com.djcoldbrain.giflib.model.Gif;
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
public class GifDaoImpl implements GifDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Gif> findAll() {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Gif> query = session.getCriteriaBuilder().createQuery(Gif.class);
        Root<Gif> root = query.from(Gif.class);
        Query<Gif> q  = session.createQuery(query);
        List<Gif> gifs = q.getResultList();
        session.close();
        return gifs;
    }

    @Override
    public List<Gif> findAllStartWith(String q) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Gif> query = builder.createQuery(Gif.class);
        Root<Gif> root = query.from(Gif.class);

        query.select(root).where(builder.like(root.get("description"), "%"+ q +"%"));
        Query<Gif> query1  = session.createQuery(query);
        List<Gif> gifs = query1.getResultList();
        session.close();
        return gifs;
    }

    @Override
    public Gif findById(long id) {

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Gif> query = builder.createQuery(Gif.class);
        Root<Gif> root = query.from(Gif.class);
        query.select(root).where(builder.equal(root.get("id"), id));
        Query<Gif> q  = session.createQuery(query);
        Gif gif = q.getSingleResult();
        session.close();
        return gif;
    }

    @Override
    public void save(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(gif);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Gif gif) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(gif);
        session.getTransaction().commit();
        session.close();
    }
}
