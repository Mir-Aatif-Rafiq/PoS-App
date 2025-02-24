package com.pos.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.ClientPojo;

@Repository
public class ClientDao {

    private static String delete_id = "delete from ClientPojo p where id=:id";
    private static String select_id = "select p from ClientPojo p where id=:id";
    private static String select_all = "select p from ClientPojo p";

    private int id;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ClientPojo cp) {
        em.persist(cp);
    }

    @Transactional
    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional
    public ClientPojo select(int id) {
        TypedQuery<ClientPojo> query = getQuery(select_id);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public List<ClientPojo> selectAll() {
        TypedQuery<ClientPojo> query = getQuery(select_all);
        return query.getResultList();
    }

    @Transactional  // SUPPOSED TO PUT A ROLLBACK Statement here ****
    public void update(int id, ClientPojo cp) {
        ClientPojo temp_cp = this.select(id);
        temp_cp.setClient_name(cp.getClient_name());
    }

    public TypedQuery<ClientPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ClientPojo.class);
    }

}
