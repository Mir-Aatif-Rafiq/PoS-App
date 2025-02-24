package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductForm {
    public String product_name;
    public int client_id;
    public int product_price;
    public int product_quantity;
}
