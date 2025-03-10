package com.pos.app.dao;

import com.pos.app.pojo.DaySalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@Transactional(rollbackOn = Exception.class)
public class DaySalesDao extends AbstractDao {

    private static final String SELECT_BY_DATE_RANGE = "SELECT ds FROM DaySalesPojo ds WHERE ds.reportDate BETWEEN :startDate AND :endDate";

    @PersistenceContext
    private EntityManager em;

    public List<DaySalesPojo> findByDateRange(ZonedDateTime startDate, ZonedDateTime endDate) {
        TypedQuery<DaySalesPojo> query = getQuery(SELECT_BY_DATE_RANGE);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public void insert(DaySalesPojo daySalesPojo) {
        em.persist(daySalesPojo);
    }

    public TypedQuery<DaySalesPojo> getQuery(String jpql) {
        return em.createQuery(jpql, DaySalesPojo.class);
    }
}
