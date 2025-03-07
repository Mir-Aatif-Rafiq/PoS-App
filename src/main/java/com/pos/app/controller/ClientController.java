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
        try {
            clientDto.insert(clientForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating client: " + e.getMessage());
        }
    }
    
    @ApiOperation(value = "Update an existing client")
    @RequestMapping(path = "/api/clients/{clientId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateClient(@PathVariable int clientId, @RequestBody ClientForm updatedClientForm) {
        try {
            if (clientId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client ID must be positive");
            }
            
            clientDto.updateClient(clientId, updatedClientForm);
            return ResponseEntity.ok("Client updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating client: " + e.getMessage());
        }
    }
    
    @ApiOperation(value = "Get client by ID")
    @RequestMapping(path = "/api/clients/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<?> getClient(@PathVariable int clientId) {
        try {
            if (clientId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Client ID must be positive");
            }
            
            ClientData clientData = clientDto.getClient(clientId);
            return ResponseEntity.ok(clientData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving client: " + e.getMessage());
        }
    }
    
    @ApiOperation(value = "Get all clients")
    @RequestMapping(path = "/api/clients", method = RequestMethod.GET)
    public ResponseEntity<?> getAllClients() {
        try {
            List<ClientData> clientDataList = clientDto.getAllClients();
            return ResponseEntity.ok(clientDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving clients: " + e.getMessage());
        }
    }
}
