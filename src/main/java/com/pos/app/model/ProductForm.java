package com.pos.app.model;

public class ProductForm {
    public String product_name;
    public int client_id;
    public int product_price;
    public int product_quantity;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int price) {
        this.product_price = price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int quantity) {
        this.product_quantity = quantity;
    }
}
