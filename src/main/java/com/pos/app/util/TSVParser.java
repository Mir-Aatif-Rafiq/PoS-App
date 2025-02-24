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
                    product.setProduct_name(fields[0]);
                    product.setProduct_barcode(Integer.parseInt(fields[1]));
                    product.setProduct_price(Integer.parseInt(fields[2]));
                    product.setProduct_quantity(Integer.parseInt(fields[3]));
                    product.setClient_id(Integer.parseInt(fields[4]));
                    product.setProduct_image_link(fields[5]);
                    productList.add(product);
                }
            }
        }
        return productList;
    }
}
