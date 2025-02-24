package com.pos.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.pos.app.dto.ProductDto;
import com.pos.app.flow.ProductFlow;
import com.pos.app.model.ProductData;
import com.pos.app.model.ProductForm;
import com.pos.app.pojo.ProductPojo;
import com.pos.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ProductController {

    @Autowired
    ProductDto pd;
    @Autowired
    ProductService ps;
    @Autowired
    ProductFlow p_flow;   // remove later

    @RequestMapping(value = "/api/products/uploadInventory", method = RequestMethod.POST)
    public String uploadInventory(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return "redirect:/products/uploadFailed";
            }
            List<ProductPojo> products = parseTsvFile(file);
            for(ProductPojo pp : products) {
                ps.insert(pp);
            }
            return "redirect:/inventory";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/products/uploadFailed";
        }
    }

    private List<ProductPojo> parseTsvFile(MultipartFile file) throws IOException {
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
                    product.setProduct_quantity(Integer.parseInt(fields[2]));
                    product.setClient_pojo(p_flow.getClientPojo(Integer.parseInt(fields[2])));
                    product.setProduct_image_link(fields[5]);
                    productList.add(product);
                }
            }
        }
        return productList;
    }

    @ApiOperation(value = "INSERT")
    @RequestMapping(path = "/api/products", method = RequestMethod.POST)
    public void insert(@RequestBody ProductForm pf){
        pd.insert(pf);
    }
    @ApiOperation(value = "UPDATE")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm new_pf){
        pd.update(id, new_pf);
    }
    @ApiOperation(value = "GET")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id){
        return pd.get(id);
    }
    @ApiOperation(value = "GET ALL")
    @RequestMapping(path = "/api/products", method = RequestMethod.GET)
    public List<ProductData> getAll(){
        return pd.getAll();
    }
    @ApiOperation(value = "DELETE")
    @RequestMapping(path = "/api/products/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        pd.delete(id);
    }




}
