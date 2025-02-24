package com.pos.app.pojo;

import javax.persistence.*;

@Entity
public class ProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String product_name;
    private int product_barcode;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private ClientPojo client_pojo;

    private int product_price;
    private int product_quantity;
    private String product_image_link;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public ClientPojo getClient_pojo() {
        return client_pojo;
    }

    public void setClient_pojo(ClientPojo client_pojo) {
        this.client_pojo = client_pojo;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(int product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getProduct_image_link() {
        return product_image_link;
    }

    public void setProduct_image_link(String product_image_link) {
        this.product_image_link = product_image_link;
    }
}
