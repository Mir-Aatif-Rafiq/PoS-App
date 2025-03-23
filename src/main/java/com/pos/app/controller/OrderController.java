package com.pos.app.controller;

import com.pos.app.dto.OrderDto;
import com.pos.app.exception.ApiException;
import com.pos.app.model.InvoiceData;
import com.pos.app.model.OrderData;
import com.pos.app.model.OrderDirectoryData;
import com.pos.app.model.OrderForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    private RestTemplate restTemplate;


    @Value("${invoiceServiceUrl}")
    private String invoiceServiceUrl;

    @Value("${invoiceServiceUrlGet}")
    private String invoiceServiceUrlGet;

    @Value("${invoiceServiceUrlGetForEntity}")
    private String invoiceServiceUrlGetForEntity;

    @ApiOperation(value = "Create a new order")
    @RequestMapping(path = "/api/order-directory/create-order", method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody List<OrderForm> orderFormList) {
        try {
            orderDto.insert(orderFormList);
            return ResponseEntity.ok("Order created successfully");
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating order: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get orders by order ID")
    @RequestMapping(path = "/api/order-directory/order-id/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrdersByOrderId(@PathVariable Integer orderId) {
            List<OrderData> orderDataList = orderDto.getOrdersByOrderId(orderId);
            return ResponseEntity.ok(orderDataList);
    }

    @ApiOperation(value = "Get all order directories")
    @RequestMapping(path = "/api/order-directory", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrderDirectories() {
            List<OrderDirectoryData> orderDirectoryDataList = orderDto.getAllOrderDirectories();
            return ResponseEntity.ok(orderDirectoryDataList);
    }

    @ApiOperation(value = "Get order directory by ID")
    @RequestMapping(path = "/api/order-directory/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getOrderDirectory(@PathVariable Integer orderId) {
            OrderDirectoryData orderDirectoryData = orderDto.getOrderDirectory(orderId);
            return ResponseEntity.ok(orderDirectoryData);
    }

    @ApiOperation(value = "Get all orders")
    @RequestMapping(path = "/api/order-directory/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getAllOrders() {
            List<OrderData> orderDataList = orderDto.getAllOrders();
            return ResponseEntity.ok(orderDataList);
    }

    @ApiOperation(value = "Generate Invoice for Order Id")
    @RequestMapping(path = "/api/order-directory/generate-invoice/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> generateInvoice(@PathVariable Integer orderId) {

            List<OrderData> orderDataList = orderDto.getOrdersByOrderId(orderId);
            ResponseEntity<String> response = restTemplate.postForEntity(invoiceServiceUrl, orderDataList, String.class);
            String base64Pdf = response.getBody();
            byte[] pdfBytes = orderDto.pdfDecoder(base64Pdf);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice_" + orderId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
    }

    @ApiOperation(value = "GET ALL INVOICES")
    @RequestMapping(path = "/api/order-directory/get-invoice", method = RequestMethod.GET)
    public ResponseEntity<?> getInvoice() {
            ResponseEntity<InvoiceData[]> response = restTemplate.getForEntity(invoiceServiceUrlGet, InvoiceData[].class);
            List<InvoiceData> invoices = Arrays.asList(response.getBody());
            return ResponseEntity.ok(invoices);
    }


}
