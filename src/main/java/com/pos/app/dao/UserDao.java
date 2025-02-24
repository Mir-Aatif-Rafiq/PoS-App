package com.pos.app.dao;

import com.pos.app.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UserDao extends AbstractDao{

    private static String select_id = "select p from UserPojo p where id=:id";
    private static String select_email = "select p from UserPojo p where email=:email";
    private static String select_all = "select p from UserPojo p";

    @Transactional
    public void insert(UserPojo p) {
        em().persist(p);
    }
    @Transactional
    public void delete(int id) {
        UserPojo temp_up = this.select(id);
        temp_up.setDeleted_at(LocalDateTime.now());
    }
    @Transactional
    public void delete(String email) {
        UserPojo temp_up = this.select(email);
        temp_up.setDeleted_at(LocalDateTime.now());
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
