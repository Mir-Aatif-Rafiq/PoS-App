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
    ClientService cs;
    @Autowired
    ProductService ps;

    public ClientPojo getClientPojo(int client_id){
        return cs.get(client_id);
    }

    public String getClientName(ProductPojo pp) {
        return cs.get(pp.getClient_id()).getClient_name();
    }

}
