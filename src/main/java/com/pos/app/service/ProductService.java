package com.pos.app.service;

import com.pos.app.dao.ProductDao;
import com.pos.app.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    public ProductDao pd;

    @Transactional
    public void insert(ProductPojo pp){
        pd.insert(pp);
    }

    @Transactional
    public ProductPojo get(int id){
        return pd.select(id);
    }

    @Transactional
    public List<ProductPojo> getAll(){
        return pd.selectAll();
    }

    @Transactional
    public void update(int id, ProductPojo new_pp){
        pd.update(id,new_pp);
    }

    @Transactional
    public void delete(int id){
        pd.delete(id);
        return;
    }
}
