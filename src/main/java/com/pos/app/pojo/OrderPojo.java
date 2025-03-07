package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Orders")
@Setter
@Getter
public class OrderPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotNull(message = "Product barcode cannot be null")
    @Column(name = "product_barcode")
    private int productBarcode;
    
    @NotNull(message = "Client ID cannot be null")
    @Column(name = "client_id")
    private int clientId;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
    
    @NotNull(message = "Total price cannot be null")
    @Min(value = 0, message = "Total price cannot be negative")
    @Column(name = "total_price")
    private int totalPrice;
    
    @NotNull(message = "Order ID cannot be null")
    @Column(name = "order_id")
    private int orderId;
}



