package com.pos.app.dao;

import com.pos.app.pojo.DaySalesPojo;
import com.pos.app.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class DaySalesDao {

    private final static String select = "SELECT p FROM PosDaySalesPojo p WHERE p.reportDate BETWEEN :startDate AND :endDate";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<DaySalesPojo> findByDateRange(ZonedDateTime startDate, ZonedDateTime endDate){
        TypedQuery<DaySalesPojo> query = getQuery(select);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Transactional
    public void insert(DaySalesPojo daySalesPojo) {
        em.persist(daySalesPojo);
    }

    public TypedQuery<DaySalesPojo> getQuery(String jpql) {
        return em.createQuery(jpql, DaySalesPojo.class);
    }


}
