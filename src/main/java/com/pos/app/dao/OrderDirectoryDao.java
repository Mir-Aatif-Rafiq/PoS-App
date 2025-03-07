package com.pos.app.dao;

import com.pos.app.pojo.OrderDirectoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@Transactional(rollbackOn = Exception.class)
public class OrderDirectoryDao extends AbstractDao {

    private static final String SELECT_BY_ORDER_ID = "select od from OrderDirectoryPojo od where orderId=:orderId";
    private static final String SELECT_ALL = "select od from OrderDirectoryPojo od";
    private static final String SELECT_BY_DATE = "SELECT od FROM OrderDirectoryPojo od WHERE od.createdAt BETWEEN :startDate AND :endDate";

    @PersistenceContext
    private EntityManager em;

    public void insert(OrderDirectoryPojo orderDirectoryPojo) {
        em.persist(orderDirectoryPojo);
    }

    public OrderDirectoryPojo select(int orderId) {
        TypedQuery<OrderDirectoryPojo> query = getQuery(SELECT_BY_ORDER_ID);
        query.setParameter("orderId", orderId);
        return query.getSingleResult();
    }

    public List<OrderDirectoryPojo> selectByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        TypedQuery<OrderDirectoryPojo> query = getQuery(SELECT_BY_DATE);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<OrderDirectoryPojo> selectAll() {
        TypedQuery<OrderDirectoryPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public void update(int totalPrice, OrderDirectoryPojo orderDirectoryPojo) {
        OrderDirectoryPojo existingOrderDirectory = select(orderDirectoryPojo.getOrderId());
        existingOrderDirectory.setTotalPrice(totalPrice);
    }

    public TypedQuery<OrderDirectoryPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderDirectoryPojo.class);
    }
}
