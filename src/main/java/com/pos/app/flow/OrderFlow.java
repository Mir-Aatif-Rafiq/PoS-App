package com.pos.app.flow;

import com.pos.app.pojo.ProductPojo;
import com.pos.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderFlow {
    @Autowired
    ProductService ps;

    public int getTotal_price(int barcode, int quantity){
        ProductPojo pp = ps.getByProduct_barcode(barcode);
        return pp.getProduct_price()*quantity;
    }
    public int getClient_id(int barcode){
        ProductPojo pp = ps.getByProduct_barcode(barcode);
        return pp.getClient_id();
    }
    public String getProductName(int barcode) {
        return ps.getByProduct_barcode(barcode).getProduct_name();
    }
    public void reduceInventory(int barcode, int quantity){
        ProductPojo pp = ps.getByProduct_barcode(barcode);
        pp.setProduct_quantity(pp.getProduct_quantity() - quantity);
        ps.update(pp.getProduct_id(),pp);
    }

}
