package com.pos.app.dao;

import com.pos.app.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDao extends AbstractDao{

    private static String delete_id = "delete from UserPojo p where id=:id";
    private static String delete_email = "delete from UserPojo p where email=:email";
    private static String select_id = "select p from UserPojo p where id=:id";
    private static String select_email = "select p from UserPojo p where email=:email";
    private static String select_all = "select p from UserPojo p";

    @Transactional
    public void insert(UserPojo p) {
        em().persist(p);
    }
    @Transactional
    public int delete(int id) {
        Query query = em().createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
    @Transactional
    public int delete(String email) {
        Query query = em().createQuery(delete_email);
        query.setParameter("email", email);
        return query.executeUpdate();
    }
    @Transactional
    public UserPojo select(int id) {
        TypedQuery<UserPojo> query = getQuery(select_id, UserPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    @Transactional
    public UserPojo select(String email) {
        TypedQuery<UserPojo> query = getQuery(select_email, UserPojo.class);
        query.setParameter("email", email);
        return getSingle(query);
    }
    @Transactional
    public List<UserPojo> selectAll() {
        TypedQuery<UserPojo> query = getQuery(select_all, UserPojo.class);
        return query.getResultList();
    }
    @Transactional
    public void update(UserPojo p) {
    }

}
