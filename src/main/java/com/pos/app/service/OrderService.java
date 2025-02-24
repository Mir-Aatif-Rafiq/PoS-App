package com.pos.app.service;

import com.pos.app.dao.OrderDao;
import com.pos.app.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {


    @Autowired
    public OrderDao od;

    @Transactional
    public void insert(OrderPojo op) {
        od.insert(op);
    }

    @Transactional
    public void get(int id) {
        od.select(id);
    }

    @Transactional
    public List<OrderPojo> getAll() {
        return od.selectAll();
    }

    @Transactional
    public void update(int id, OrderPojo new_op) {
        od.update(id, new_op);
    }
    @Transactional
    public void delete(int id){
        od.delete(id);
    }

}

