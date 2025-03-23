package com.pos.app.service;

import com.pos.app.dao.ProductDao;
import com.pos.app.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
        if (productDao.barcodeExists(productPojo.getProductBarcode())) {
            throw new IllegalArgumentException("Product barcode already exists");
        }
        
        if (productPojo.getProductPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        
        productDao.insert(productPojo);
    }

    public ProductPojo getProductById(Integer productId) {
        return productDao.selectById(productId);
    }

    public ProductPojo getProductByBarcode(Integer barcode) {
        return productDao.selectByProductBarcode(barcode);
    }

    public List<ProductPojo> getAllProducts() {
        return productDao.selectAll();
    }

    public void updateProduct(Integer productId, ProductPojo updatedProductPojo) {
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


    public List<ProductPojo> parseTsvFile(MultipartFile file) throws IOException {
        List<ProductPojo> productPojoList = new ArrayList<ProductPojo>();

        Path originalFilePath = Paths.get("src/main/resources/uploads/" + file.getOriginalFilename());
        Path tempFilePath = Paths.get(originalFilePath.toString() + ".tmp");
        File tempFile = tempFilePath.toFile();

        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile,true))
        ) {
            String line;
            int rowNum = 1;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");

                if (fields.length < 6) {
                    writer.write(line + "\tERROR: Missing required fields in row " + rowNum + "\n");
                    rowNum++;
                    continue;
                }

                try {
                    String productName = fields[0].trim();
                    int barcode = Integer.parseInt(fields[1].trim());
                    double price = Double.parseDouble(fields[2].trim());
                    int quantity = Integer.parseInt(fields[3].trim());
                    int clientId = Integer.parseInt(fields[4].trim());
                    String productImageLink = fields[5].trim();

                    if (barcode <= 0 || price <= 0 || quantity < 0 || clientId <= 0) {
                        writer.write(line + "\tERROR: Invalid numeric values in row " + rowNum + "\n");
                        rowNum++;
                        continue;
                    }
                    if(productDao.barcodeExists(barcode)){
                        writer.write(line + "\tERROR: Barcode already exists in row " + rowNum + "\n");
                        rowNum++;
                        continue;
                    }

                    if (productName.isEmpty()) {
                        writer.write(line + "\tERROR: Product name is empty in row " + rowNum + "\n");
                        rowNum++;
                        continue;
                    }

                    ProductPojo product = new ProductPojo();
                    product.setProductName(productName);
                    product.setProductBarcode(barcode);
                    product.setProductPrice(price);
                    product.setProductQuantity(quantity);
                    product.setClientId(clientId);
                    product.setProductImageLink(productImageLink);

                    writer.write(line + "\tVALID\n");

                    productPojoList.add(product);

                } catch (NumberFormatException e) {
                    writer.write(line + "\tERROR: Invalid number format in row " + rowNum + "\n");
                    rowNum++;
                }

                rowNum++;
            }
        }

        Files.move(tempFile.toPath(), originalFilePath, StandardCopyOption.REPLACE_EXISTING);
            return productPojoList;
    }
}
