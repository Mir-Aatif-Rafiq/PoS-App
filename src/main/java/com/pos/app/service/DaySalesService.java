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
public class DaySalesService {
    @Autowired
    DaySalesFlow daySalesFlow;
    @Autowired
    DaySalesDao daySalesDao;

    @Transactional
    public void generateSalesReport(ZonedDateTime startDate, ZonedDateTime endDate){
        List<OrderDirectoryPojo> invoicedOrders = daySalesFlow.getOrderByDate(startDate, endDate);

        int totalOrders = invoicedOrders.size();
        int totalItemsSold = invoicedOrders.stream().mapToInt(OrderDirectoryPojo::getTotalItems).sum();
        int totalRevenue = invoicedOrders.stream().mapToInt(OrderDirectoryPojo::getTotal_price).sum();

        DaySalesPojo salesReport = new DaySalesPojo();
        salesReport.setTotalInvoicedOrders(totalOrders);
        salesReport.setTotalItemsSold(totalItemsSold);
        salesReport.setTotalRevenueGenerated(totalRevenue);
        salesReport.setReportDate(startDate);

        daySalesDao.insert(salesReport);
    }

    @Transactional
    public void generateDailySalesReport() {
        ZonedDateTime todayStart = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS);
        ZonedDateTime todayEnd = todayStart.plusDays(1).minusNanos(1);
        generateSalesReport(todayStart, todayEnd);
    }

    @Transactional
    public List<DaySalesPojo> getByDate(ZonedDateTime startDate, ZonedDateTime endDate){
        return daySalesDao.findByDateRange(startDate, endDate);
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void runDailySalesReport() {
        System.out.println("Running daily sales report job...");
        generateDailySalesReport();
        System.out.println("Daily sales report job completed.");
    }
}
