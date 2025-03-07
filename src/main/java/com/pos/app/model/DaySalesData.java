package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class DaySalesData {
    private Integer id;
    private Integer totalInvoicedOrders;
    private Integer totalItemsSold;
    private Integer totalRevenueGenerated;
    private ZonedDateTime reportDate;
}
