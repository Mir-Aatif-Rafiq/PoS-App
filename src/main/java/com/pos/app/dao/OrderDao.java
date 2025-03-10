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
@Transactional(rollbackOn = Exception.class)
public class OrderDao extends AbstractDao {

    private static final String SELECT_BY_ID = "select o from OrderPojo o where id=:id";
    private static final String SELECT_ALL = "select o from OrderPojo o";
    private static final String SELECT_BY_ORDER_ID = "select o from OrderPojo o where orderId=:orderId";
    private static final String SELECT_BY_CLIENT_ID = "select c from OrderPojo o where clientId=:clientId";

    @PersistenceContext
    private EntityManager em;

    public void insert(OrderPojo orderPojo) {
        em.persist(orderPojo);
    }

    public OrderPojo selectById(Integer orderId) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("id", orderId);
        return query.getSingleResult();
    }

    public List<OrderPojo> selectByOrderId(Integer orderId) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_ORDER_ID);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
    public List<OrderPojo> selectByClientId(Integer clientId) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_CLIENT_ID);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public TypedQuery<OrderPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderPojo.class);
    }
}
