package com.pos.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.ClientDto;
import com.pos.app.model.ClientData;
import com.pos.app.model.ClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ClientController {

    @Autowired
    ClientDto cd;

    @ApiOperation(value = "INSERT")
    @RequestMapping(path = "/api/clients", method = RequestMethod.POST)
    public void insert(@RequestBody ClientForm cf){
        cd.insert(cf);
    }
    @ApiOperation(value = "UPDATE")
    @RequestMapping(path = "/api/clients/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody ClientForm new_cf){
        cd.update(id, new_cf);
    }
    @ApiOperation(value = "GET")
    @RequestMapping(path = "/api/clients/{id}", method = RequestMethod.GET)
    public ClientData get(@PathVariable int id){
        return cd.get(id);
    }
    @ApiOperation(value = "GET ALL")
    @RequestMapping(path = "/api/clients", method = RequestMethod.GET)
    public List<ClientData> getAll(){
        return cd.getAll();
    }
    @ApiOperation(value = "DELETE")
    @RequestMapping(path = "/api/clients/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        cd.delete(id);
    }
}
