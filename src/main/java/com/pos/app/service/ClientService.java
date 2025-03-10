package com.pos.app.service;

import com.pos.app.dao.ClientDao;
import com.pos.app.pojo.ClientPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ClientService {
    @Autowired
    private ClientDao clientDao;

    public void insertClient(ClientPojo clientPojo) {
        if (clientPojo == null) {
            throw new IllegalArgumentException("Client cannot be null");
        }
        
        if (clientPojo.getClientName() == null || clientPojo.getClientName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty");
        }
        
        clientDao.insert(clientPojo);
    }
    
    public ClientPojo getClient(Integer clientId) {
        return clientDao.select(clientId);
    }

    public List<ClientPojo> getAllClients() {
        return clientDao.selectAll();
    }

    public void updateClient(Integer clientId, ClientPojo updatedClientPojo) {
        if (updatedClientPojo == null) {
            throw new IllegalArgumentException("Updated client cannot be null");
        }
        
        if (updatedClientPojo.getClientName() == null || updatedClientPojo.getClientName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty");
        }
        
        clientDao.update(clientId, updatedClientPojo);
    }
}
