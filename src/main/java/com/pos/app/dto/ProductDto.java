package com.pos.app.dto;

import com.pos.app.flow.ProductFlow;
import com.pos.app.model.ProductData;
import com.pos.app.model.ProductForm;
import com.pos.app.pojo.ProductPojo;
import com.pos.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductDto {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductFlow productFlow;

    public ProductPojo formToPojo(ProductForm productForm) {
        if (productForm == null) {
            throw new IllegalArgumentException("Product form cannot be null");
        }
        
        if (productForm.getProductName() == null || productForm.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        
        if (productForm.getProductBarcode() <= 0) {
            throw new IllegalArgumentException("Product barcode must be positive");
        }
        
        if (productForm.getProductPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        
        if (productForm.getProductQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        
        ProductPojo productPojo = new ProductPojo();
        productPojo.setProductName(productForm.getProductName());
        productPojo.setClientId(productForm.getClientId());
        productPojo.setProductBarcode(productForm.getProductBarcode());
        productPojo.setClientName(productFlow.getClientName(productPojo));
        productPojo.setProductPrice(productForm.getProductPrice());
        productPojo.setProductQuantity(productForm.getProductQuantity());
        productPojo.setProductImageLink(productForm.getProductImageLink());
        return productPojo;
    }
    
    public ProductData pojoToData(ProductPojo productPojo) {
        if (productPojo == null) {
            throw new IllegalArgumentException("Product pojo cannot be null");
        }
        
        ProductData productData = new ProductData();
        productData.setProductName(productPojo.getProductName());
        productData.setProductId(productPojo.getProductId());
        productData.setProductBarcode(productPojo.getProductBarcode());
        productData.setClientId(productPojo.getClientId());
        productData.setClientName(productFlow.getClientName(productPojo));
        productData.setProductPrice(productPojo.getProductPrice());
        productData.setProductQuantity(productPojo.getProductQuantity());
        productData.setCreatedAt(productPojo.getCreatedAt());
        productData.setUpdatedAt(productPojo.getUpdatedAt());
        productData.setProductImageLink(productPojo.getProductImageLink());
        return productData;
    }

    public void insertProduct(ProductForm productForm) {
        if (productForm == null) {
            throw new IllegalArgumentException("Product form cannot be null");
        }
        
        productService.insertProduct(formToPojo(productForm));
    }

    public ProductData getProductById(Integer productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        
        ProductPojo productPojo = productService.getProductById(productId);
        if (productPojo == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        
        return pojoToData(productPojo);
    }

    public ProductData getProductByBarcode(Integer barcode) {
        if (barcode <= 0) {
            throw new IllegalArgumentException("Product barcode must be positive");
        }
        
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        if (productPojo == null) {
            throw new IllegalArgumentException("Product not found with barcode: " + barcode);
        }
        
        return pojoToData(productPojo);
    }

    public List<ProductData> getAllProducts() {
        List<ProductPojo> productPojoList = productService.getAllProducts();
        List<ProductData> productDataList = new ArrayList<>();
        
        for (ProductPojo productPojo : productPojoList) {
            ProductData productData = pojoToData(productPojo);
            productDataList.add(productData);
        }
        
        return productDataList;
    }

    public void updateProduct(Integer productId, ProductForm updatedProductForm) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }
        
        if (updatedProductForm == null) {
            throw new IllegalArgumentException("Updated product form cannot be null");
        }
        
        productService.updateProduct(productId, formToPojo(updatedProductForm));
    }
}

