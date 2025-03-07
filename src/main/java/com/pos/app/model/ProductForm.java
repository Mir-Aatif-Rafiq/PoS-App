package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Setter
@Getter
public class ProductForm {
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String productName;
    
    @NotNull(message = "Product barcode cannot be null")
    @Min(value = 1, message = "Product barcode must be positive")
    private int productBarcode;
    
    @NotNull(message = "Client ID cannot be null")
    @Min(value = 1, message = "Client ID must be positive")
    private int clientId;
    
    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Product price cannot be negative")
    private int productPrice;
    
    @NotNull(message = "Product quantity cannot be null")
    @Min(value = 0, message = "Product quantity cannot be negative")
    private int productQuantity;

    private String productImageLink;
}
