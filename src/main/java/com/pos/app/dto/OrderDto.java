package com.pos.app.dto;

import com.pos.app.flow.OrderFlow;
import com.pos.app.model.OrderData;
import com.pos.app.model.OrderDirectoryData;
import com.pos.app.model.OrderForm;
import com.pos.app.pojo.OrderDirectoryPojo;
import com.pos.app.pojo.OrderPojo;
import com.pos.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDto {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderFlow orderFlow;

    public OrderPojo formToOrderPojo(OrderForm orderForm) {
        if (orderForm == null) {
            throw new IllegalArgumentException("Order form cannot be null");
        }
        
        if (orderForm.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setProductBarcode(orderForm.getProductBarcode());
        orderPojo.setQuantity(orderForm.getQuantity());
        orderPojo.setTotalPrice(orderFlow.getTotal_price(orderForm.getProductBarcode(), orderForm.getQuantity()));
        orderPojo.setClientId(orderFlow.getClient_id(orderForm.getProductBarcode()));
        return orderPojo;
    }
    
    public OrderData orderPojoToOrderData(OrderPojo orderPojo) {
        if (orderPojo == null) {
            throw new IllegalArgumentException("Order pojo cannot be null");
        }
        
        OrderData orderData = new OrderData();
        orderData.setId(orderPojo.getId());
        orderData.setName(orderFlow.getProductName(orderPojo.getProductBarcode()));
        orderData.setProductBarcode(orderPojo.getProductBarcode());
        orderData.setQuantity(orderPojo.getQuantity());
        orderData.setTotalPrice(orderPojo.getTotalPrice());
        orderData.setOrderId(orderPojo.getOrderId());
        orderData.setClientId(orderPojo.getClientId());
        orderData.setCreatedAt(orderPojo.getCreatedAt());
        return orderData;
    }
    
    public OrderDirectoryData orderDirectoryPojoToOrderData(OrderDirectoryPojo orderDirectoryPojo) {
        if (orderDirectoryPojo == null) {
            throw new IllegalArgumentException("Order directory pojo cannot be null");
        }
        
        OrderDirectoryData orderDirectoryData = new OrderDirectoryData();
        orderDirectoryData.setOrderId(orderDirectoryPojo.getOrderId());
        orderDirectoryData.setTotalPrice(orderDirectoryPojo.getTotalPrice());
        orderDirectoryData.setTotalItems(orderDirectoryPojo.getTotalItems());
        orderDirectoryData.setCreatedAt(orderDirectoryPojo.getCreatedAt());
        return orderDirectoryData;
    }

    public void insert(List<OrderForm> orderFormList) {
        if (orderFormList == null || orderFormList.isEmpty()) {
            throw new IllegalArgumentException("Order form list cannot be null or empty");
        }
        
        OrderDirectoryPojo orderDirectoryPojo = new OrderDirectoryPojo();
        Double totalPrice = 0.0;
        Integer totalItems = orderFormList.size();
        orderDirectoryPojo.setTotalItems(totalItems);
        orderService.insertOrderDirectory(orderDirectoryPojo);
        
        for (OrderForm orderForm : orderFormList) {
            OrderPojo orderPojo = formToOrderPojo(orderForm);
            totalPrice += orderPojo.getTotalPrice();

            orderFlow.reduceInventory(orderPojo.getProductBarcode(), orderPojo.getQuantity());
            orderPojo.setOrderId(orderDirectoryPojo.getOrderId());
            orderService.insertOrder(orderPojo);
        }
        
        orderService.updateOrderDirectoryTotalPrice(totalPrice, orderDirectoryPojo);
    }

    public OrderData getOrderById(Integer orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        
        OrderPojo orderPojo = orderService.getOrderById(orderId);
        if (orderPojo == null) {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
        
        return orderPojoToOrderData(orderPojo);
    }

    public List<OrderData> getOrdersByOrderId(Integer orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        
        List<OrderPojo> orderPojoList = orderService.getOrdersByOrderId(orderId);
        List<OrderData> orderDataList = new ArrayList<>();
        
        for (OrderPojo orderPojo : orderPojoList) {
            orderDataList.add(orderPojoToOrderData(orderPojo));
        }
        
        return orderDataList;
    }
    
    public List<OrderData> getAllOrders() {
        List<OrderPojo> orderPojoList = orderService.getAllOrders();
        List<OrderData> orderDataList = new ArrayList<>();
        
        for (OrderPojo orderPojo : orderPojoList) {
            orderDataList.add(orderPojoToOrderData(orderPojo));
        }
        
        return orderDataList;
    }
    
    public OrderDirectoryData getOrderDirectory(Integer orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }
        
        OrderDirectoryPojo orderDirectoryPojo = orderService.getOrderDirectory(orderId);
        if (orderDirectoryPojo == null) {
            throw new IllegalArgumentException("Order directory not found with ID: " + orderId);
        }
        
        return orderDirectoryPojoToOrderData(orderDirectoryPojo);
    }

    public List<OrderDirectoryData> getAllOrderDirectories() {
        List<OrderDirectoryPojo> orderDirectoryPojoList = orderService.getAllOrderDirectories();
        List<OrderDirectoryData> orderDirectoryDataList = new ArrayList<>();
        
        for (OrderDirectoryPojo orderDirectoryPojo : orderDirectoryPojoList) {
            OrderDirectoryData orderDirectoryData = orderDirectoryPojoToOrderData(orderDirectoryPojo);
            orderDirectoryDataList.add(orderDirectoryData);
        }
        
        return orderDirectoryDataList;
    }
}
