package com.pos.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.ClientDto;
import com.pos.app.model.ClientData;
import com.pos.app.model.ClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ClientController {

    @Autowired
    private ClientDto clientDto;

    @ApiOperation(value = "Insert a new client")
    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public ResponseEntity<?> insertClient(@RequestBody ClientForm clientForm) {
            clientDto.insert(clientForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client created successfully");
    }
    
    @ApiOperation(value = "Update an existing client")
    @RequestMapping(path = "/api/admin/clients/{clientId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@PathVariable Integer clientId, @RequestBody ClientForm updatedClientForm) {
            clientDto.updateClient(clientId, updatedClientForm);
            return ResponseEntity.ok("Client updated successfully");
    }
    
    @ApiOperation(value = "Get client by ID")
    @RequestMapping(path = "/api/clients/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<?> getClientById(@PathVariable Integer clientId) {
            ClientData clientData = clientDto.getClientById(clientId);
            return ResponseEntity.ok(clientData);
    }

    @ApiOperation(value = "Get client by ID")
    @RequestMapping(path = "/api/clients/{clientName}", method = RequestMethod.GET)
    public ResponseEntity<?> getClientById(@PathVariable String clientName) {
            ClientData clientData = clientDto.getClientByName(clientName);
            return ResponseEntity.ok(clientData);
    }
    
    @ApiOperation(value = "Get all clients")
    @RequestMapping(path = "/api/clients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllClients() {
        List<ClientData> clientDataList = clientDto.getAllClients();
        return ResponseEntity.ok(clientDataList);
    }

}
