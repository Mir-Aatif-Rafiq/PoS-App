package com.pos.app.flow;

import com.pos.app.pojo.OrderDirectoryPojo;
import com.pos.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
public class DaySalesFlow {
    @Autowired
    OrderService orderService;


    public List<OrderDirectoryPojo> getOrderByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        return orderService.getByDate(startDate,endDate);
    }


//    List<DaySalesPojo> getTotalRevenue(ZonedDateTime startDate, ZonedDateTime endDate){
//
//    }
//    List<DaySalesPojo> getTotalRevenue(ZonedDateTime startDate, ZonedDateTime endDate){
//
//    }
//    List<DaySalesPojo> getTotalRevenue(ZonedDateTime startDate, ZonedDateTime endDate){
//
//    }
}
