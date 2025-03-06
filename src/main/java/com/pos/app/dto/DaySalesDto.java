package com.pos.app.dto;


import com.pos.app.model.DaySalesData;
import com.pos.app.pojo.DaySalesPojo;
import com.pos.app.service.DaySalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DaySalesDto {

    @Autowired
    DaySalesService daySalesService;

    public DaySalesData pojoToData(DaySalesPojo daySalesPojo){
        DaySalesData daySalesData = new DaySalesData();
        daySalesData.setId(daySalesPojo.getId());
        daySalesData.setReportDate(daySalesPojo.getReportDate());
        daySalesData.setTotalInvoicedOrders(daySalesPojo.getTotalInvoicedOrders());
        daySalesData.setTotalItemsSold(daySalesPojo.getTotalItemsSold());
        daySalesData.setTotalRevenueGenerated(daySalesPojo.getTotalRevenueGenerated());
        return daySalesData;
    }

    public List<DaySalesData> getByDate(ZonedDateTime startDate, ZonedDateTime endDate){
        List<DaySalesPojo> daySalesPojoList = daySalesService.getByDate(startDate,endDate);
        List<DaySalesData> daySalesDataList = new ArrayList<DaySalesData>();
        for( DaySalesPojo pojo : daySalesPojoList){
            daySalesDataList.add(pojoToData(pojo));
        }
        return daySalesDataList;
    }

    public void generateDaySales(ZonedDateTime startDate, ZonedDateTime endDate){
        daySalesService.generateSalesReport(startDate, endDate);
    }

}
