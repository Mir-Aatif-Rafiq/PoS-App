package com.pos.app.dto;

import com.pos.app.model.ClientData;
import com.pos.app.model.ClientForm;
import com.pos.app.pojo.ClientPojo;
import com.pos.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientDto {
    
    @Autowired
    private ClientService clientService;

    public ClientPojo formToPojo(ClientForm clientForm) {
        if (clientForm == null) {
            throw new IllegalArgumentException("Client form cannot be null");
        }
        
        if (clientForm.getName() == null || clientForm.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty");
        }
        
        ClientPojo clientPojo = new ClientPojo();
        clientPojo.setClientName(clientForm.getName());
        return clientPojo;
    }
    
    public ClientData pojoToData(ClientPojo clientPojo) {
        if (clientPojo == null) {
            throw new IllegalArgumentException("Client pojo cannot be null");
        }
        
        ClientData clientData = new ClientData();
        clientData.setName(clientPojo.getClientName());
        clientData.setId(clientPojo.getClientId());
        clientData.setCreatedAt(clientPojo.getCreatedAt());
        clientData.setUpdatedAt(clientPojo.getUpdatedAt());
        return clientData;
    }

    public void insert(ClientForm clientForm) {
        if (clientForm == null) {
            throw new IllegalArgumentException("Client form cannot be null");
        }
        
        clientService.insertClient(formToPojo(clientForm));
    }

    public ClientData getClient(Integer clientId) {
        if (clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be positive");
        }
        
        ClientPojo clientPojo = clientService.getClient(clientId);
        if (clientPojo == null) {
            throw new IllegalArgumentException("Client not found with ID: " + clientId);
        }
        
        return pojoToData(clientPojo);
    }

    public List<ClientData> getAllClients() {
        List<ClientPojo> clientPojoList = clientService.getAllClients();
        List<ClientData> clientDataList = new ArrayList<>();
        
        for (ClientPojo clientPojo : clientPojoList) {
            ClientData clientData = pojoToData(clientPojo);
            clientDataList.add(clientData);
        }
        
        return clientDataList;
    }
    
    public void updateClient(Integer clientId, ClientForm updatedClientForm) {
        if (clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be positive");
        }
        
        if (updatedClientForm == null) {
            throw new IllegalArgumentException("Updated client form cannot be null");
        }
        
        clientService.updateClient(clientId, formToPojo(updatedClientForm));
    }
}
