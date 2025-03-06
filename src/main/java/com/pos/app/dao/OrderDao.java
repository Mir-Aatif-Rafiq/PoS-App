package com.pos.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.OrderPojo;

@Repository
public class OrderDao {

    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p";
    private static String select_order_id = "select p from OrderPojo p where order_id=:order_id";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo op) {
        em.persist(op);
    }

    @Transactional
    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional
    public OrderPojo selectById(int id) {
        TypedQuery<OrderPojo> query = getQuery(select_id);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public List<OrderPojo> selectByOrder_id(int id) {
        TypedQuery<OrderPojo> query = getQuery(select_order_id);
        query.setParameter("order_id", id);
        return query.getResultList();
    }

    @Transactional
    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all);
        return query.getResultList();
    }


    public TypedQuery<OrderPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderPojo.class);
    }

}
