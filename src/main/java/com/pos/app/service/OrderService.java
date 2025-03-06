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
public class OrderService {

    @Autowired
    public OrderDao od;
    @Autowired
    public OrderDirectoryDao odd;

    @Transactional
    public void insert(OrderPojo op) {
        od.insert(op);
    }

    @Transactional
    public void insert(OrderDirectoryPojo odp) {
        odd.insert(odp);
    }

    @Transactional
    public OrderPojo getOrderById(int id) {
        return od.selectById(id);
    }

    @Transactional
    public List<OrderPojo> getOrdersByOrder_id(int id) {
        return od.selectByOrder_id(id);
    }

    @Transactional
    public OrderDirectoryPojo get(int id) {
        return odd.select(id);
    }

    @Transactional
    public List<OrderDirectoryPojo> getAll() {
        return odd.selectAll();
    }

    @Transactional
    public List<OrderPojo> getAllOrders() {
        return od.selectAll();
    }

    @Transactional
    public List<OrderDirectoryPojo> getByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        return odd.selectByDate(startDate,endDate);
    }

    @Transactional
    public void update(int total_price , OrderDirectoryPojo odp) { odd.update(total_price,odp);}
}

