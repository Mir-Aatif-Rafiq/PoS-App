package com.pos.app.dao;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.OrderPojo;

@Repository
@Transactional(rollbackOn = Exception.class)
public class OrderDao extends AbstractDao<OrderPojo> {

    private static final String SELECT_BY_ID = "select o from OrderPojo o where id=:id";
    private static final String SELECT_ALL = "select o from OrderPojo o";
    private static final String SELECT_BY_ORDER_ID = "select o from OrderPojo o where orderId=:orderId";
    private static final String SELECT_BY_CLIENT_ID = "select o from OrderPojo o where clientId=:clientId";
    private static final String SELECT_BY_BARCODE = "select o from OrderPojo o where productBarcode=:productBarcode";
    private static final String SELECT_BY_DATE_RANGE =
            "SELECT o FROM OrderPojo o WHERE createdAt BETWEEN :startDate AND :endDate";


    public OrderDao(){
        super(OrderPojo.class);
    }

    public void insert(OrderPojo orderPojo) {
        em().persist(orderPojo);
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

    public List<OrderPojo> selectByBarcode(Integer barcode) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_BARCODE);
        query.setParameter("productBarcode", barcode);
        return query.getResultList();
    }

    public List<OrderPojo> selectByDateRange(ZonedDateTime startDate, ZonedDateTime endDate) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_BY_DATE_RANGE);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

}
