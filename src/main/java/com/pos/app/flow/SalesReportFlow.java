package com.pos.app.flow;

import com.pos.app.dto.OrderDto;
import com.pos.app.model.OrderData;
import com.pos.app.pojo.OrderDirectoryPojo;
import com.pos.app.pojo.OrderPojo;
import com.pos.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalesReportFlow {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDto orderDto;

    public List<OrderDirectoryPojo> getOrderByDate(ZonedDateTime startDate, ZonedDateTime endDate) {
        return orderService.getOrderDirectoriesByDate(startDate,endDate);
    }
    public List<OrderData> getOrderByClientId(Integer clientId){
        List<OrderPojo> orderPojoList = orderService.getOrdersByClientId(clientId);
        List<OrderData> orderDataList = new ArrayList<OrderData>();
        for(OrderPojo orderPojo : orderPojoList){
            orderDataList.add(orderDto.orderPojoToOrderData(orderPojo));
        }
        return orderDataList;
    }
}
