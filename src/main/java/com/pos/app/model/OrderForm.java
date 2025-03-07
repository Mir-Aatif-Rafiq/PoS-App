package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;

@Getter
@Setter
public class OrderForm {
    @NotNull(message = "Product barcode cannot be null")
    @Min(value = 1, message = "Product barcode must be positive")
    private int productBarcode;
    
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
