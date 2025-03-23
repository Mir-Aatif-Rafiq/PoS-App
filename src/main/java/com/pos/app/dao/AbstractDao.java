package com.pos.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Optional;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;

@Transactional
public abstract class AbstractDao<T> {

    @PersistenceContext
    private EntityManager em;

    private final Class<T> entityClass;

    protected AbstractDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Optional<T> getSingle(TypedQuery<T> query) {
        return query.getResultList().stream().findFirst();
    }

    protected TypedQuery<T> getQuery(String jpql) {
        return em.createQuery(jpql, entityClass);
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

