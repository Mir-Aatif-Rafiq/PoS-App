package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class ProductPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String product_name;
    private int product_barcode;
    private String client_name;
    private int client_id;
    private int product_price;
    private int product_quantity;
    private String product_image_link;
    public String getProduct_name() {
        return product_name;
    }
}
