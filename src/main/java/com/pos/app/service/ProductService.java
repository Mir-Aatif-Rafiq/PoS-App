package com.pos.app.service;

import com.pos.app.dao.ProductDao;
import com.pos.app.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public void insertProduct(ProductPojo productPojo) {
        if (productPojo == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        if (productPojo.getProductName() == null || productPojo.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        
        if (productPojo.getProductBarcode() <= 0) {
            throw new IllegalArgumentException("Product barcode must be positive");
        }
        
        if (productPojo.getProductPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        
        productDao.insert(productPojo);
    }

    public ProductPojo getProductById(int productId) {
        return productDao.selectById(productId);
    }

    public ProductPojo getProductByBarcode(int barcode) {
        return productDao.selectByProductBarcode(barcode);
    }

    public List<ProductPojo> getAllProducts() {
        return productDao.selectAll();
    }

    public void updateProduct(int productId, ProductPojo updatedProductPojo) {
        if (updatedProductPojo == null) {
            throw new IllegalArgumentException("Updated product cannot be null");
        }
        
        if (updatedProductPojo.getProductName() == null || updatedProductPojo.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        
        if (updatedProductPojo.getProductBarcode() <= 0) {
            throw new IllegalArgumentException("Product barcode must be positive");
        }
        
        if (updatedProductPojo.getProductPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        
        productDao.update(productId, updatedProductPojo);
    }
}
