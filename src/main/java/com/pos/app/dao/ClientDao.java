package com.pos.app.dao;

import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.ClientPojo;

@Repository
@Transactional(rollbackOn = Exception.class)
public class ClientDao extends AbstractDao {

    private static final String SELECT_BY_ID = "select c from ClientPojo c where clientId=:clientId";
    private static final String SELECT_ALL = "select c from ClientPojo c";
    private static final String COUNT_BY_NAME = "select count(c) from ClientPojo c where clientName=:clientName";

    @PersistenceContext
    private EntityManager em;

    public void insert(ClientPojo clientPojo) {
        if (clientNameExists(clientPojo.getClientName())) {
            throw new IllegalArgumentException("Client name already exists: " + clientPojo.getClientName());
        }
        
        em.persist(clientPojo);
    }

    public ClientPojo select(Integer clientId) {
        TypedQuery<ClientPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("clientId", clientId);
        return query.getSingleResult();
    }

    public List<ClientPojo> selectAll() {
        TypedQuery<ClientPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public void update(Integer clientId, ClientPojo clientPojo) {
        ClientPojo existingClient = this.select(clientId);
        
        if (!clientPojo.getClientName().equals(existingClient.getClientName()) && 
            clientNameExists(clientPojo.getClientName())) {
            throw new IllegalArgumentException("Cannot update: Client name already exists: " + clientPojo.getClientName());
        }
        
        existingClient.setClientName(clientPojo.getClientName());
    }
    
    public boolean clientNameExists(String clientName) {
        TypedQuery<Long> query = em.createQuery(COUNT_BY_NAME, Long.class);
        query.setParameter("clientName", clientName);
        return query.getSingleResult() > 0;
    }

    public TypedQuery<ClientPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ClientPojo.class);
    }
}
