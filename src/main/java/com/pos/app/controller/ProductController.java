package com.pos.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.ProductDto;
import com.pos.app.model.ProductData;
import com.pos.app.model.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@MultipartConfig
public class ProductController {

    @Autowired
    private ProductDto productDto;
    
    @ApiOperation(value = "Upload inventory from TSV file")
    @RequestMapping(value = "/api/admin/products/uploadInventory", method = RequestMethod.POST)
    public ResponseEntity<?> uploadInventory(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] fileContent = productDto.uploadInventory(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentLength(fileContent.length)
                .contentType(MediaType.parseMediaType("text/tab-separated-values"))
                .body(fileContent);
    }

    @ApiOperation(value = "Insert a new product")
    @RequestMapping(path = "/api/products", method = RequestMethod.POST)
    public ResponseEntity<?> insertProduct(@RequestBody ProductForm productForm) {
            productDto.insertProduct(productForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
    }

    @ApiOperation(value = "Update an existing product")
    @RequestMapping(path = "/api/admin/products/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductForm updatedProductForm) {
        productDto.updateProduct(productId, updatedProductForm);
            return ResponseEntity.ok("Product updated successfully");
    }

    @ApiOperation(value = "Get product by ID")
    @RequestMapping(path = "/api/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
            ProductData productData = productDto.getProductById(productId);
            return ResponseEntity.ok(productData);
    }

    @ApiOperation(value = "Get product by barcode")
    @RequestMapping(path = "/api/products/barcode/{barcode}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductByBarcode(@PathVariable Integer barcode) {
            ProductData productData = productDto.getProductByBarcode(barcode);
            return ResponseEntity.ok(productData);
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts() {
            List<ProductData> productDataList = productDto.getAllProducts();
            return ResponseEntity.ok(productDataList);
    }
}
