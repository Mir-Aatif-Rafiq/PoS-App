package com.pos.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.ProductPojo;

@Repository
@Transactional(rollbackOn = Exception.class)
public class ProductDao extends AbstractDao {

    private static final String SELECT_BY_ID = "select p from ProductPojo p where productId=:productId";
    private static final String SELECT_BY_BARCODE = "select p from ProductPojo p where productBarcode=:productBarcode";
    private static final String SELECT_ALL = "select p from ProductPojo p";
    private static final String COUNT_BY_ID = "select count(p) from ProductPojo p where productId=:productId";
    private static final String COUNT_BY_BARCODE = "select count(p) from ProductPojo p where productBarcode=:productBarcode";

    @PersistenceContext
    private EntityManager em;

    public void insert(ProductPojo productPojo) {
        if (barcodeExists(productPojo.getProductBarcode())) {
            throw new IllegalArgumentException("Product barcode already exists: " + productPojo.getProductBarcode());
        }
        
        em.persist(productPojo);
    }

    public ProductPojo selectById(int productId) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_ID);
        query.setParameter("productId", productId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public ProductPojo selectByProductBarcode(int productBarcode) {
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

    public void update(int productId, ProductPojo productPojo) {
        ProductPojo existingProduct = this.selectById(productId);
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        
        if (productPojo.getProductBarcode() != existingProduct.getProductBarcode() && 
            barcodeExists(productPojo.getProductBarcode())) {
            throw new IllegalArgumentException("Cannot update: Product barcode already exists: " + productPojo.getProductBarcode());
        }
        
        existingProduct.setProductName(productPojo.getProductName());
        existingProduct.setProductBarcode(productPojo.getProductBarcode());
        existingProduct.setClientId(productPojo.getClientId());
        existingProduct.setClientName(productPojo.getClientName());
        existingProduct.setProductPrice(productPojo.getProductPrice());
        existingProduct.setProductQuantity(productPojo.getProductQuantity());
        existingProduct.setProductImageLink(productPojo.getProductImageLink());
    }
    
    public boolean productExists(int productId) {
        return exists(productId, COUNT_BY_ID, "productId");
    }
    
    public boolean barcodeExists(int barcode) {
        TypedQuery<Long> query = em.createQuery(COUNT_BY_BARCODE, Long.class);
        query.setParameter("productBarcode", barcode);
        return query.getSingleResult() > 0;
    }

    public TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }
}
