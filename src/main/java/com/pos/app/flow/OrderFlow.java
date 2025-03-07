package com.pos.app.flow;

import com.pos.app.pojo.ProductPojo;
import com.pos.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderFlow {
    @Autowired
    ProductService productService;

    public int getTotal_price(int barcode, int quantity){
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        return productPojo.getProductPrice()*quantity;
    }
    public int getClient_id(int barcode){
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        return productPojo.getClientId();
    }
    public String getProductName(int barcode) {
        return productService.getProductByBarcode(barcode).getProductName();
    }
    public void reduceInventory(int barcode, int quantity){
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        productPojo.setProductQuantity(productPojo.getProductQuantity() - quantity);
        productService.updateProduct(productPojo.getProductId(),productPojo);
    }

}
