package com.pos.app.controller;

import com.pos.app.dto.OrderDto;
import com.pos.app.model.OrderData;
import com.pos.app.model.OrderDirectoryData;
import com.pos.app.model.OrderForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Api
@RestController
public class OrderController {

    @Autowired
    OrderDto od;
    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "INSERT")
    @RequestMapping(path = "/api/order-directory/create-order", method = RequestMethod.POST)
    public void insert(@RequestBody List<OrderForm> l_of){
        od.insert(l_of);
    }

    @ApiOperation(value = "GET")
    @RequestMapping(path = "/api/order-directory/order-id/{order_id}", method = RequestMethod.GET)
    public List<OrderData> get(@PathVariable int order_id){
        return od.getOrdersByOrder_id(order_id);
    }

    @ApiOperation(value = "GET ALL")
    @RequestMapping(path = "/api/order-directory", method = RequestMethod.GET)
    public List<OrderDirectoryData> getAll(){
        return od.getAll();
    }

    @ApiOperation(value = "GET ORDER DIRECTORY")
    @RequestMapping(path = "/api/order-directory/{id}", method = RequestMethod.GET)
    public OrderDirectoryData getOrderDirectory(@PathVariable int id){
        return od.get(id);
    }

    @ApiOperation(value = "GET ALL ORDERS")
    @RequestMapping(path = "/api/order-directory/orders", method = RequestMethod.GET)
    public List<OrderData> getAllOrders(){
        return od.getAllOrders();
    }

    @ApiOperation(value = "INVOICE GENERATION")
    @RequestMapping(path = "/api/order-directory/get-invoice/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getInvoice(@PathVariable Integer id){
        if (id == null) {
            return ResponseEntity.badRequest().body("Missing order ID!");
        }
        List<OrderData> l_order_data = get(id);
        System.out.println("Im here id is " + id);
        String invoiceServiceUrl = "http://localhost:9200/invoice/api/generate";
        ResponseEntity<String> response = restTemplate.postForEntity(invoiceServiceUrl, l_order_data, String.class);
        return ResponseEntity.ok(response.getBody());
    }


    // delete order, re-populate inventory?
//    @ApiOperation(value = "DELETE")
//    @RequestMapping(path = "/api/clients/{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable int id){
//        cd.delete(id);
//    }
}
