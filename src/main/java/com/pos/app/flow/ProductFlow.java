package com.pos.app.flow;

import com.pos.app.pojo.ClientPojo;
import com.pos.app.pojo.ProductPojo;
import com.pos.app.service.ClientService;
import com.pos.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProductFlow {
    @Autowired
    ClientService clientService;

    public ClientPojo getClientPojo(Integer client_id){
        return clientService.getClient(client_id);
    }

    public String getClientName(ProductPojo productPojo) {
        return clientService.getClient(productPojo.getClientId()).getClientName();
    }

}
