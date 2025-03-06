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
public class OrderDirectoryDao {

    private static String select_id = "select p from OrderDirectoryPojo p where order_id=:order_id";
    private static String select_all = "select p from OrderDirectoryPojo p";
    private static String select_by_date = "SELECT p FROM OrderDirectoryPojo p WHERE p.created_at BETWEEN :startDate AND :endDate";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderDirectoryPojo odp) {
        em.persist(odp);
    }

    @Transactional
    public OrderDirectoryPojo select(int id) {
        TypedQuery<OrderDirectoryPojo> query = getQuery(select_id);
        query.setParameter("order_id", id);
        return query.getSingleResult();
    }
    @Transactional
    public List<OrderDirectoryPojo> selectByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        TypedQuery<OrderDirectoryPojo> query = getQuery(select_by_date);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Transactional
    public List<OrderDirectoryPojo> selectAll() {
        TypedQuery<OrderDirectoryPojo> query = getQuery(select_all);
        return query.getResultList();
    }
    @Transactional
    public void update(int total_price, OrderDirectoryPojo odp) {
        OrderDirectoryPojo temp_odp = select(odp.getOrder_id());
        temp_odp.setTotal_price(total_price);
    }

    public TypedQuery<OrderDirectoryPojo> getQuery(String jpql) {
        return em.createQuery(jpql, OrderDirectoryPojo.class);
    }
}
