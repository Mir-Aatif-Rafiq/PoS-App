package com.pos.app.dao;

import com.pos.app.pojo.DaySalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional(rollbackOn = Exception.class)
public class DaySalesDao extends AbstractDao<DaySalesPojo> {

    private static final String SELECT_ALL = "SELECT ds FROM DaySalesPojo ds";

    public DaySalesDao(){
        super(DaySalesPojo.class);
    }
    public void insert(DaySalesPojo daySalesPojo) {
        em().persist(daySalesPojo);
    }

    public List<DaySalesPojo> getAll(){
        TypedQuery<DaySalesPojo> query = getQuery(SELECT_ALL);
        return query.getResultList();
    }
}
