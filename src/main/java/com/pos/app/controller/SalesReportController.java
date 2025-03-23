package com.pos.app.controller;

import com.pos.app.dto.SalesReportDto;
import com.pos.app.model.DaySalesData;
import com.pos.app.model.SalesReportData;
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
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Api
@RestController
public class SalesReportController {

    @Autowired
    private SalesReportDto salesReportDto;


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @ApiOperation(value = "Get all day sales")
    @RequestMapping(path = "/api/admin/daily-sales", method = RequestMethod.GET)
    public ResponseEntity<?> getDailySalesByDateRange() {
        try {
            List<DaySalesData> daySalesDataList = salesReportDto.getAllDaySales();
            return ResponseEntity.ok(daySalesDataList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving sales reports: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Generate sales report for date range")
    @RequestMapping("/api/admin/sales-report/date/{startDate}/{endDate}")
    public ResponseEntity<?> generateDailySalesReport(@PathVariable String startDate,
                                                      @PathVariable String endDate) {
            LocalDate startLocalDate = LocalDate.parse(startDate, DATE_FORMATTER);
            LocalDate endLocalDate = LocalDate.parse(endDate, DATE_FORMATTER);

            ZonedDateTime startDateTime = startLocalDate.atStartOfDay(ZoneId.of("UTC"));
            ZonedDateTime endDateTime = endLocalDate.atStartOfDay(ZoneId.of("UTC"));

            SalesReportData salesReportData = salesReportDto.generateSalesReportForDateRange(startDateTime, endDateTime);

            return ResponseEntity.ok(salesReportData);
    }

    @ApiOperation(value = "Generate sales report for client")
    @RequestMapping(path = "/api/admin/sales-report/client/{clientId}", method = RequestMethod.GET)
    public ResponseEntity<?> generateSalesReportForClient(@PathVariable Integer clientId) {
        SalesReportData salesReportData = salesReportDto.getSalesReportForClient(clientId);
        return ResponseEntity.ok(salesReportData);
    }

    @ApiOperation(value = "Generate sales report for barcode")
    @RequestMapping(path = "/api/admin/sales-report/barcode/{barcode}", method = RequestMethod.GET)
    public ResponseEntity<?> generateSalesReportForBarcode(@PathVariable Integer barcode) {
        SalesReportData salesReportData = salesReportDto.getSalesReportForBarcode(barcode);
        return ResponseEntity.ok(salesReportData);
    }
}
