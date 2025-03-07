package com.pos.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional
public abstract class AbstractDao {
    @PersistenceContext
    private EntityManager em;

    protected <T> T getSingle(TypedQuery<T> query) {
        try {
            return query.getResultList().stream().findFirst().orElse(null);
        } catch (NoResultException e) {
            return null;
        }
    }
    
    protected <T> TypedQuery<T> getQuery(String jpql, Class<T> clazz) {
        return em.createQuery(jpql, clazz);
    }
    
    protected EntityManager em() {
        return em;
    }
    
    protected boolean exists(int id, String query, String paramName) {
        TypedQuery<Long> countQuery = em.createQuery(query, Long.class);
        countQuery.setParameter(paramName, id);
        return countQuery.getSingleResult() > 0;
    }
}
