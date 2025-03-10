package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Order_directory")
@Setter
@Getter
public class OrderDirectoryPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    
    @NotNull(message = "Total items cannot be null")
    @Min(value = 1, message = "Total items must be at least 1")
    @Column(name = "total_items")
    private Integer totalItems;
    
    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price cannot be negative")
    @Column(name = "total_price")
    private Double totalPrice;
}
