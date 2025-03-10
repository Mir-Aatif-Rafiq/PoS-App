package com.pos.app.controller;

import com.pos.app.dto.SalesReportDto;
import com.pos.app.model.DaySalesData;
import com.pos.app.model.OrderData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@Api
@RestController
public class SalesReportController {

    @Autowired
    private SalesReportDto salesReportDto;

    @ApiOperation(value = "Get sales reports by date range")
    @RequestMapping(path = "/api/daily-sales/{startDate}/{endDate}", method = RequestMethod.GET)
    public ResponseEntity<?> getDailySalesByDateRange(@PathVariable ZonedDateTime startDate, @PathVariable ZonedDateTime endDate) {
        try {
            if (startDate == null || endDate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and end date cannot be null");
            }
            
            if (startDate.isAfter(endDate)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date cannot be after end date");
            }
            
            List<DaySalesData> daySalesDataList = salesReportDto.getSalesByDateRange(startDate, endDate);
            return ResponseEntity.ok(daySalesDataList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving sales reports: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Generate sales report for date range")
    @RequestMapping(path = "/api/daily-sales/{startDate}/{endDate}", method = RequestMethod.POST)
    public ResponseEntity<?> generateDailySalesReport(@PathVariable ZonedDateTime startDate, @PathVariable ZonedDateTime endDate) {
        try {
            if (startDate == null || endDate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and end date cannot be null");
            }
            
            if (startDate.isAfter(endDate)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date cannot be after end date");
            }
            
            salesReportDto.generateSalesReport(startDate, endDate);
            return ResponseEntity.ok("Sales report generated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error generating sales report: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Generate sales report for client")
    @RequestMapping(path = "/api/sales-report/{clientId}", method = RequestMethod.POST)
    public ResponseEntity<?> generateSalesReportForClient(@PathVariable Integer clientId) {
        try {
            if (clientId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("clientId cannot be null");
            }

            List<OrderData> orderDataList = salesReportDto.getSalesReportForClient(clientId);
            return ResponseEntity.ok(orderDataList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating sales report: " + e.getMessage());
        }
    }
}
