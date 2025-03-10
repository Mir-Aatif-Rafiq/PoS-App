package com.pos.app.service;

import com.pos.app.dao.DaySalesDao;
import com.pos.app.flow.DaySalesFlow;
import com.pos.app.pojo.DaySalesPojo;
import com.pos.app.pojo.OrderDirectoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class DaySalesService {
    @Autowired
    private DaySalesFlow daySalesFlow;
    
    @Autowired
    private DaySalesDao daySalesDao;

    public void generateSalesReport(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        List<OrderDirectoryPojo> invoicedOrders = daySalesFlow.getOrderByDate(startDate, endDate);

        Integer totalOrders = invoicedOrders.size();
        Integer totalItemsSold = invoicedOrders.stream().mapToInt(OrderDirectoryPojo::getTotalItems).sum();
        Double totalRevenue = invoicedOrders.stream().mapToDouble(OrderDirectoryPojo::getTotalPrice).sum();

        DaySalesPojo salesReport = new DaySalesPojo();
        salesReport.setTotalInvoicedOrders(totalOrders);
        salesReport.setTotalItemsSold(totalItemsSold);
        salesReport.setTotalRevenueGenerated(totalRevenue);
        salesReport.setReportDate(startDate);

        daySalesDao.insert(salesReport);
    }

    public void generateDailySalesReport() {
        ZonedDateTime todayStart = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime todayEnd = todayStart.plusDays(1).minusNanos(1);
        generateSalesReport(todayStart, todayEnd);
    }

    public List<DaySalesPojo> getSalesByDateRange(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        return daySalesDao.findByDateRange(startDate, endDate);
    }

//    @Scheduled(cron = "0 */3 * * * *")
//    public void runDailySalesReport() {
//        System.out.prIntegerln("Running daily sales report job...");
//        generateDailySalesReport();
//        System.out.prIntegerln("Daily sales report job completed.");
//    }
}
