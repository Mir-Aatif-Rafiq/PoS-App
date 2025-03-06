package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
    private int product_barcode;
    private int quantity;

}
