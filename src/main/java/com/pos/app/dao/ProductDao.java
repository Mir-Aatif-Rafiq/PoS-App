package com.pos.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.pos.app.pojo.ProductPojo;

@Repository
public class ProductDao {

    private static String delete_id = "delete from ProductPojo p where id=:id";
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";

    private int id;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo cp) {
        em.persist(cp);
    }

    @Transactional
    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional
    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all);
        return query.getResultList();
    }

    @Transactional  // SUPPOSED TO PUT A ROLLBACK Statement here ****
    public void update(int id, ProductPojo pp) {
        ProductPojo temp_pp = this.select(id);
        temp_pp.setProduct_name(pp.getProduct_name());
        temp_pp.setProduct_barcode(pp.getProduct_barcode());
        temp_pp.setProduct_price(pp.getProduct_price());
        temp_pp.setProduct_quantity(pp.getProduct_quantity());
        temp_pp.setProduct_image_link(pp.getProduct_image_link());
    }

    public TypedQuery<ProductPojo> getQuery(String jpql) {
        return em.createQuery(jpql, ProductPojo.class);
    }

}
