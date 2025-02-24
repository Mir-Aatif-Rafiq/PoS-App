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

import java.util.List;

@Api
@RestController
public class ProductController {

    @Autowired
    ProductDto pd;
    @Autowired
    ProductService ps;

    @RequestMapping(value = "/api/products/uploadInventory", method = RequestMethod.POST)
    public ResponseEntity<?> uploadInventory(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Parsing TSV File");
            }
            List<ProductPojo> products = TSVParser.parseTsvFile(file);
            for(ProductPojo pp : products) {
                ps.insert(pp);
            }
            return ResponseEntity.ok("Parsing Successful!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Parsing TSV File");
        }
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
