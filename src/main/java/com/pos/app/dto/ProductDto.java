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
    private ProductService ps;
    @Autowired
    private ProductFlow p_flow;

    public ProductPojo formToPojo(ProductForm pf){
        ProductPojo pp = new ProductPojo();
        pp.setProduct_name(pf.getProduct_name());
        pp.setClient_id(pf.getClient_id());
        pp.setClient_name(p_flow.getClientName(pp));
        pp.setProduct_price(pf.getProduct_price());
        pp.setProduct_quantity(pf.getProduct_quantity());
        return pp;
    }
    public ProductData pojoToData(ProductPojo pp){
        ProductData pd = new ProductData();
        pd.setProduct_name(pp.getProduct_name());
        pd.setProduct_id(pp.getProduct_id());
        pd.setClient_id(pp.getClient_id());
        pd.setClient_name(p_flow.getClientName(pp));
        pd.setProduct_price(pp.getProduct_price());
        pd.setProduct_quantity(pp.getProduct_quantity());
        pd.setCreated_at(pp.getCreated_at());
        pd.setUpdated_at(pp.getUpdated_at());
        pd.setDeleted_at(pp.getDeleted_at());
        return pd;
    }

    public void insert(ProductForm pf){
        ps.insert(formToPojo(pf));
    }

    public ProductData get(int id){
        return pojoToData(ps.get(id));
    }

    public List<ProductData> getAll(){
        List<ProductPojo> l_pp = ps.getAll();
        List<ProductData> l_pd = new ArrayList<>();
        for(ProductPojo pp : l_pp){
            ProductData pd = pojoToData(pp);
            l_pd.add(pd);
        }
        return l_pd;
    }

    public void update(int id, ProductForm new_pf){
        ps.update(id,formToPojo(new_pf));
    }

    public void delete(int id){
        ps.delete(id);
    }

}

