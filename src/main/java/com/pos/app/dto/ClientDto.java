package com.pos.app.dto;

import com.pos.app.model.ClientData;
import com.pos.app.model.ClientForm;
import com.pos.app.pojo.ClientPojo;
import com.pos.app.service.ClientService;
import com.pos.app.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
        clientPojo.setClientName(StringUtil.normalize(clientForm.getName()));
        clientPojo.setClientCategory(StringUtil.normalize( clientForm.getCategory()));
        return clientPojo;
    }
    
    public ClientData pojoToData(ClientPojo clientPojo) {
        if (clientPojo == null) {
            throw new IllegalArgumentException("Client pojo cannot be null");
        }
        
        ClientData clientData = new ClientData();
        clientData.setName(clientPojo.getClientName());
        clientData.setId(clientPojo.getClientId());
        clientData.setCategory(clientPojo.getClientCategory());
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

    public ClientData getClientById(Integer clientId) {
        if (clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be positive");
        }
        
        ClientPojo clientPojo = clientService.getClientById(clientId);
        if (clientPojo == null) {
            throw new IllegalArgumentException("Client not found with ID: " + clientId);
        }
        
        return pojoToData(clientPojo);
    }

    public ClientData getClientByName(String clientName) {

        ClientPojo clientPojo = clientService.getClientByName(clientName);
        if (clientPojo == null) {
            throw new IllegalArgumentException("Client not found with name: " + clientName);
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
