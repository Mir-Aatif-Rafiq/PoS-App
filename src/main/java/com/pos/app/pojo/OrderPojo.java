package com.pos.app.pojo;

import javax.persistence.*;

@Entity
public class OrderPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderDirectoryPojo order_directory;
//    private int order_id;  // to be generated based on order start time.
    private int product_barcode;
    private int quantity;
    private int price;
    private int customer_id;
    private int date;

    public int getId() {
        return id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(int product_barcode) {
        this.product_barcode = product_barcode;
    }

    public OrderDirectoryPojo getOrder_directory() {
        return order_directory;
    }

    public void setOrder_directory(OrderDirectoryPojo order_directory) {
        this.order_directory = order_directory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}



