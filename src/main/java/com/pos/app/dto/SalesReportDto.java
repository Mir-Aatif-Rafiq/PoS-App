package com.pos.app.dto;

import com.pos.app.flow.SalesReportFlow;
import com.pos.app.model.DaySalesData;
import com.pos.app.model.OrderData;
import com.pos.app.pojo.DaySalesPojo;
import com.pos.app.service.DaySalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SalesReportDto {

    @Autowired
    private DaySalesService daySalesService;
    @Autowired
    private SalesReportFlow salesReportFlow;

    public DaySalesData pojoToData(DaySalesPojo daySalesPojo) {
        if (daySalesPojo == null) {
            throw new IllegalArgumentException("Day sales pojo cannot be null");
        }
        
        DaySalesData daySalesData = new DaySalesData();
        daySalesData.setId(daySalesPojo.getId());
        daySalesData.setReportDate(daySalesPojo.getReportDate());
        daySalesData.setTotalInvoicedOrders(daySalesPojo.getTotalInvoicedOrders());
        daySalesData.setTotalItemsSold(daySalesPojo.getTotalItemsSold());
        daySalesData.setTotalRevenueGenerated(daySalesPojo.getTotalRevenueGenerated());
        return daySalesData;
    }

    public List<DaySalesData> getSalesByDateRange(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        List<DaySalesPojo> daySalesPojoList = daySalesService.getSalesByDateRange(startDate, endDate);
        List<DaySalesData> daySalesDataList = new ArrayList<>();
        
        for (DaySalesPojo daySalesPojo : daySalesPojoList) {
            daySalesDataList.add(pojoToData(daySalesPojo));
        }
        
        return daySalesDataList;
    }

    public void generateSalesReport(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        daySalesService.generateSalesReport(startDate, endDate);
    }

    public List<OrderData> getSalesReportForClient(Integer clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("ClientId cannot be null");
        }

        return salesReportFlow.getOrderByClientId(clientId);
    }
}
