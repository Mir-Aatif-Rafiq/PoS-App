package com.pos.app.util;

import com.pos.app.pojo.ProductPojo;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TSVParser {
    public static List<ProductPojo> parseTsvFile(MultipartFile file) throws IOException {
        List<ProductPojo> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");
                if (fields.length >= 6) {
                    ProductPojo product = new ProductPojo();
                    product.setProductName(fields[0]);
                    product.setProductBarcode(Integer.parseInt(fields[1]));
                    product.setProductPrice(Double.parseDouble(fields[2]));
                    product.setProductQuantity(Integer.parseInt(fields[3]));
                    product.setClientId(Integer.parseInt(fields[4]));
                    product.setProductImageLink(fields[5]);
                    productList.add(product);
                }
            }
        }
        return productList;
    }
}
