package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Products", uniqueConstraints = {
    @UniqueConstraint(columnNames = "product_barcode", name = "uk_product_barcode")
})
@Setter
@Getter
public class ProductPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Product barcode cannot be null")
    @Column(name = "product_barcode", nullable = false)
    private Integer productBarcode; // SKUs

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_id")
    private Integer clientId;

    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Product price cannot be negative")
    @Column(name = "product_price")
    private Double productPrice;

    @NotNull(message = "Product quantity cannot be null")
    @Min(value = 0, message = "Product quantity cannot be negative")
    @Column(name = "product_quantity")
    private Integer productQuantity;
    
    @Column(name = "product_image_link")
    private String productImageLink;
}
