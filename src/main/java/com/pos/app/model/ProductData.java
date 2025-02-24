package com.pos.app.model;

public class ProductData extends ProductForm {
    private int product_id;
    public void setProduct_id(int id){
        this.product_id = id;
    }

    public int getProduct_id() {
        return product_id;
    }
}
