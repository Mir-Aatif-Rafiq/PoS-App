package com.pos.app.dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.ProductPojo;

@Repository
@Transactional(rollbackOn = Exception.class)
public class ProductDao extends AbstractDao<ProductPojo> {

    private static final String SELECT_BY_ID = "select p from ProductPojo p where productId=:productId";
    private static final String SELECT_BY_BARCODE = "select p from ProductPojo p where productBarcode=:productBarcode";
    private static final String SELECT_ALL = "select p from ProductPojo p";
    private static final String COUNT_BY_ID = "select count(p) from ProductPojo p where productId=:productId";
    private static final String COUNT_BY_BARCODE = "select count(p) from ProductPojo p where productBarcode=:productBarcode";

    public ProductDao(){
        super(ProductPojo.class);
    }
    public void insert(ProductPojo productPojo) {
        if (barcodeExists(productPojo.getProductBarcode())) {
            throw new IllegalArgumentException("Product barcode already exists: " + productPojo.getProductBarcode());
        }

        em().persist(productPojo);
    }

    public ProductPojo selectById(Integer productId) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("productId", productId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductPojo selectByProductBarcode(Integer productBarcode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE);
        query.setParameter("productBarcode", productBarcode);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }

    public void update(Integer productId, ProductPojo productPojo) {
        ProductPojo existingProduct = this.selectById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        if (Objects.nonNull(productPojo.getProductName())) {
            existingProduct.setProductName(productPojo.getProductName());
        }
        if (Objects.nonNull(productPojo.getProductBarcode())) {
            existingProduct.setProductBarcode(productPojo.getProductBarcode());
        }
        if (Objects.nonNull(productPojo.getClientId())) {
            existingProduct.setClientId(productPojo.getClientId());
        }
        if (Objects.nonNull(productPojo.getClientName())) {
            existingProduct.setClientName(productPojo.getClientName());
        }
        if (Objects.nonNull(productPojo.getProductPrice())) {
            existingProduct.setProductPrice(productPojo.getProductPrice());
        }
        if (Objects.nonNull(productPojo.getProductQuantity())) {
            existingProduct.setProductQuantity(productPojo.getProductQuantity());
        }
        if (Objects.nonNull(productPojo.getProductImageLink())) {
            existingProduct.setProductImageLink(productPojo.getProductImageLink());
        }
    }
    
    public boolean productExists(Integer productId) {
        return exists(productId, COUNT_BY_ID, "productId");
    }
    
    public boolean barcodeExists(Integer barcode) {
        TypedQuery<Long> query = em().createQuery(COUNT_BY_BARCODE, Long.class);
        query.setParameter("productBarcode", barcode);
        return query.getSingleResult() > 0;
    }

}
