package com.pos.app.controller;

import com.pos.app.util.TSVParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.ProductDto;
import com.pos.app.flow.ProductFlow;
import com.pos.app.model.ProductData;
import com.pos.app.model.ProductForm;
import com.pos.app.pojo.ProductPojo;
import com.pos.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
@MultipartConfig
public class ProductController {

    @Autowired
    private ProductDto productDto;
    
    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Upload inventory from TSV file")
    @RequestMapping(value = "/api/products/uploadInventory", method = RequestMethod.POST)
    public ResponseEntity<?> uploadInventory(@RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File cannot be empty");
            }

            List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines()
                    .collect(Collectors.toList());

            if (lines.size() > 5000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: File exceeds 5000-row limit.");
            }

            Path filePath = Paths.get("src/main/resources/uploads/" + file.getOriginalFilename());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            List<ProductPojo> products = TSVParser.parseTsvFile(file);

            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No products found in file");
            }

            for (ProductPojo productPojo : products) {
                productService.insertProduct(productPojo);
            }
            
            return ResponseEntity.ok("Successfully imported " + products.size() + " products");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error parsing TSV file: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Insert a new product")
    @RequestMapping(path = "/api/products", method = RequestMethod.POST)
    public ResponseEntity<?> insertProduct(@RequestBody ProductForm productForm) {
        try {
            productDto.insertProduct(productForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating product: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Update an existing product")
    @RequestMapping(path = "/api/products/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductForm updatedProductForm) {
        try {
            if (productId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID must be positive");
            }
            
            productDto.updateProduct(productId, updatedProductForm);
            return ResponseEntity.ok("Product updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get product by ID")
    @RequestMapping(path = "/api/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductById(@PathVariable int productId) {
        try {
            if (productId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product ID must be positive");
            }
            
            ProductData productData = productDto.getProductById(productId);
            return ResponseEntity.ok(productData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving product: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get product by barcode")
    @RequestMapping(path = "/api/products/barcode/{barcode}", method = RequestMethod.GET)
    public ResponseEntity<?> getProductByBarcode(@PathVariable Integer barcode) {
        try {
            if (barcode <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Barcode must be positive");
            }
            
            ProductData productData = productDto.getProductByBarcode(barcode);
            return ResponseEntity.ok(productData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving product: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Get all products")
    @RequestMapping(path = "/api/products", method = RequestMethod.GET)
    public ResponseEntity<?> getAllProducts() {
        try {
            List<ProductData> productDataList = productDto.getAllProducts();
            return ResponseEntity.ok(productDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving products: " + e.getMessage());
        }
    }
}
