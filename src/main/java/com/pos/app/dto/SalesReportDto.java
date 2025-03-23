package com.pos.app.dto;

import com.pos.app.flow.SalesReportFlow;
import com.pos.app.model.DaySalesData;
import com.pos.app.model.OrderData;
import com.pos.app.model.SalesReportData;
import com.pos.app.pojo.DaySalesPojo;
import com.pos.app.service.DaySalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
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

    public List<DaySalesData> getAllDaySales() {

        List<DaySalesPojo> daySalesPojoList = daySalesService.getAllDaySales();
        List<DaySalesData> daySalesDataList = new ArrayList<>();

        for (DaySalesPojo daySalesPojo : daySalesPojoList) {
            daySalesDataList.add(pojoToData(daySalesPojo));
        }

        return daySalesDataList;
    }

    public SalesReportData generateSalesReportForDateRange(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        SalesReportData salesReportData = new SalesReportData();
        List<OrderData> orderDataList = salesReportFlow.getOrderByDateRange(startDate,endDate);
        salesReportData.setTotalItemsSold(0);
        salesReportData.setTotalRevenueGenerated(0.0);
        for(OrderData orderData :orderDataList ){
            salesReportData.setTotalRevenueGenerated(salesReportData.getTotalRevenueGenerated() + orderData.getTotalPrice());
            salesReportData.setTotalItemsSold(salesReportData.getTotalItemsSold() + orderData.getQuantity());
        }

        return salesReportData;
    }

    public SalesReportData getSalesReportForClient(Integer clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("ClientId cannot be null");
        }
        SalesReportData salesReportData = new SalesReportData();
        List<OrderData> orderDataList = salesReportFlow.getOrderByClientId(clientId);
        salesReportData.setTotalItemsSold(0);
        salesReportData.setTotalRevenueGenerated(0.0);
        for(OrderData orderData :orderDataList ){
            salesReportData.setTotalRevenueGenerated(salesReportData.getTotalRevenueGenerated() + orderData.getTotalPrice());
            salesReportData.setTotalItemsSold(salesReportData.getTotalItemsSold() + orderData.getQuantity());
        }

        System.out.println(salesReportData);
        System.out.println(salesReportData.getTotalRevenueGenerated());
        return salesReportData;
    }

    public SalesReportData getSalesReportForBarcode(Integer barcode) {
        if (barcode == null) {
            throw new IllegalArgumentException("Barcode cannot be null");
        }

        SalesReportData salesReportData = new SalesReportData();
        List<OrderData> orderDataList = salesReportFlow.getOrderByBarcode(barcode);
        salesReportData.setTotalItemsSold(0);
        salesReportData.setTotalRevenueGenerated(0.0);
        for(OrderData orderData :orderDataList ){
            salesReportData.setTotalRevenueGenerated(salesReportData.getTotalRevenueGenerated() + orderData.getTotalPrice());
            salesReportData.setTotalItemsSold(salesReportData.getTotalItemsSold() + orderData.getQuantity());
        }

        return salesReportData;
    }
}
