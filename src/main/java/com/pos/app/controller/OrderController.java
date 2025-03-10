package com.pos.app.controller;

import com.pos.app.dto.OrderDto;
import com.pos.app.model.InvoiceData;
import com.pos.app.model.OrderData;
import com.pos.app.model.OrderDirectoryData;
import com.pos.app.model.OrderForm;
import com.pos.app.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Api
@RestController
public class OrderController {

    @Autowired
    private OrderDto orderDto;

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "Create a new order")
    @RequestMapping(path = "/api/order-directory/create-order", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody List<OrderForm> orderFormList) {
        try {
            if (orderFormList == null || orderFormList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order form list cannot be empty");
            }
            
            orderDto.insert(orderFormList);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get orders by order ID")
    @RequestMapping(path = "/api/order-directory/order-id/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersByOrderId(@PathVariable Integer orderId) {
        try {
            if (orderId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order ID must be positive");
            }
            
            List<OrderData> orderDataList = orderDto.getOrdersByOrderId(orderId);
            return ResponseEntity.ok(orderDataList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving orders: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get all order directories")
    @RequestMapping(path = "/api/order-directory", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrderDirectories() {
        try {
            List<OrderDirectoryData> orderDirectoryDataList = orderDto.getAllOrderDirectories();
            return ResponseEntity.ok(orderDirectoryDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving order directories: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get order directory by ID")
    @RequestMapping(path = "/api/order-directory/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderDirectory(@PathVariable Integer orderId) {
        try {
            if (orderId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order ID must be positive");
            }
            
            OrderDirectoryData orderDirectoryData = orderDto.getOrderDirectory(orderId);
            return ResponseEntity.ok(orderDirectoryData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving order directory: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get all orders")
    @RequestMapping(path = "/api/order-directory/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrders() {
        try {
            List<OrderData> orderDataList = orderDto.getAllOrders();
            return ResponseEntity.ok(orderDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving orders: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Generate invoice for order")
    @RequestMapping(path = "/api/order-directory/generate-invoice/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<String> generateInvoice(@PathVariable Integer orderId) {
        try {
            if (orderId == null || orderId <= 0) {
                return ResponseEntity.badRequest().body("Invalid order ID");
            }
            
            List<OrderData> orderDataList = orderDto.getOrdersByOrderId(orderId);
            if (orderDataList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for order ID: " + orderId);
            }
            
            String invoiceServiceUrl = "http://localhost:9200/invoice/api/invoice/generate";
            ResponseEntity<String> response = restTemplate.postForEntity(invoiceServiceUrl, orderDataList, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String base64Pdf = response.getBody();
                orderService.saveDecodedPdf(base64Pdf, orderId);
                return ResponseEntity.ok("Invoice saved successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error generating invoice.");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error generating invoice: " + e.getMessage());
        }
    }

    @ApiOperation(value = "GET ALL INVOICES")
    @RequestMapping(path = "/api/order-directory/get-invoice", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoice() {
        try {
            String invoiceServiceUrl = "http://localhost:9200/invoice/api/invoice";

            ResponseEntity<InvoiceData[]> response = restTemplate.getForEntity(invoiceServiceUrl, InvoiceData[].class);

            List<InvoiceData> invoices = Arrays.asList(response.getBody());

            return ResponseEntity.ok(invoices);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching invoices: " + e.getMessage());
        }
    }

    @ApiOperation(value = "GET INVOICE BY ID")
    @RequestMapping(path = "/api/order-directory/get-invoice/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoice(@PathVariable Integer id) {
        try {
            String invoiceServiceUrl = "http://localhost:9200/invoice/api/invoice/{id}";

            ResponseEntity<InvoiceData> response = restTemplate.getForEntity(invoiceServiceUrl, InvoiceData.class,id);


            return ResponseEntity.ok(response.getBody());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching invoices: " + e.getMessage());
        }
    }

}
