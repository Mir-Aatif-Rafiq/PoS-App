package com.pos.app.service;

import com.pos.app.dao.OrderDao;
import com.pos.app.dao.OrderDirectoryDao;
import com.pos.app.pojo.OrderDirectoryPojo;
import com.pos.app.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private OrderDirectoryDao orderDirectoryDao;

    public void insertOrder(OrderPojo orderPojo) {
        if (orderPojo == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        
        if (orderPojo.getQuantity() <= 0) {
            throw new IllegalArgumentException("Order quantity must be positive");
        }
        
        orderDao.insert(orderPojo);
    }

    public void insertOrderDirectory(OrderDirectoryPojo orderDirectoryPojo) {
        if (orderDirectoryPojo == null) {
            throw new IllegalArgumentException("Order directory cannot be null");
        }
        
        if (orderDirectoryPojo.getTotalItems() <= 0) {
            throw new IllegalArgumentException("Total items must be positive");
        }
        
        orderDirectoryDao.insert(orderDirectoryPojo);
    }

    public OrderPojo getOrderById(int orderId) {
        return orderDao.selectById(orderId);
    }

    public List<OrderPojo> getOrdersByOrderId(int orderId) {
        return orderDao.selectByOrderId(orderId);
    }

    public OrderDirectoryPojo getOrderDirectory(int orderId) {
        return orderDirectoryDao.select(orderId);
    }

    public List<OrderDirectoryPojo> getAllOrderDirectories() {
        return orderDirectoryDao.selectAll();
    }

    public List<OrderPojo> getAllOrders() {
        return orderDao.selectAll();
    }

    public List<OrderDirectoryPojo> getOrderDirectoriesByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        return orderDirectoryDao.selectByDate(startDate, endDate);
    }

    public void updateOrderDirectoryTotalPrice(int totalPrice, OrderDirectoryPojo orderDirectoryPojo) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        
        if (orderDirectoryPojo == null) {
            throw new IllegalArgumentException("Order directory cannot be null");
        }
        
        orderDirectoryDao.update(totalPrice, orderDirectoryPojo);
    }
}

