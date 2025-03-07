package com.pos.app.dao;

import com.pos.app.pojo.UserPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@Transactional(rollbackOn = Exception.class)
public class UserDao extends AbstractDao {

    private static final String SELECT_BY_ID = "select u from UserPojo u where id=:id";
    private static final String SELECT_BY_EMAIL = "select u from UserPojo u where email=:email";
    private static final String SELECT_ALL = "select u from UserPojo u";
    private static final String COUNT_BY_ID = "select count(u) from UserPojo u where id=:id";
    private static final String COUNT_BY_EMAIL = "select count(u) from UserPojo u where email=:email";

    public void insert(UserPojo userPojo) {
        if (emailExists(userPojo.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userPojo.getEmail());
        }
        
        em().persist(userPojo);
    }

    public UserPojo select(int userId) {
        TypedQuery<UserPojo> query = getQuery(SELECT_BY_ID, UserPojo.class);
        query.setParameter("id", userId);
        return getSingle(query);
    }

    public UserPojo select(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        TypedQuery<UserPojo> query = getQuery(SELECT_BY_EMAIL, UserPojo.class);
        query.setParameter("email", email);
        return getSingle(query);
    }

    public List<UserPojo> selectAll() {
        TypedQuery<UserPojo> query = getQuery(SELECT_ALL, UserPojo.class);
        return query.getResultList();
    }
    
    public boolean userExists(int userId) {
        return exists(userId, COUNT_BY_ID, "id");
    }
    
    public boolean emailExists(String email) {
        TypedQuery<Long> query = em().createQuery(COUNT_BY_EMAIL, Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }
}
