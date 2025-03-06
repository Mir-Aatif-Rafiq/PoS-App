package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "day_sales")
@Setter
@Getter
public class DaySalesPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull(message = "Total invoiced orders cannot be null")
    @Min(value = 0, message = "Total invoiced orders cannot be negative")
    @Column(name = "total_invoiced_orders")
    private Integer totalInvoicedOrders;
    
    @NotNull(message = "Total items sold cannot be null")
    @Min(value = 0, message = "Total items sold cannot be negative")
    @Column(name = "total_items_sold")
    private Integer totalItemsSold;
    
    @NotNull(message = "Total revenue generated cannot be null")
    @Min(value = 0, message = "Total revenue cannot be negative")
    @Column(name = "total_revenue_generated")
    private Integer totalRevenueGenerated;
    
    @Column(name = "report_date")
    private ZonedDateTime reportDate;

    @PrePersist
    protected void onCreate() {
        if (this.reportDate == null) {
            this.reportDate = ZonedDateTime.now();
        }
    }
}
