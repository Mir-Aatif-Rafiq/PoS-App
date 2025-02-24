package com.pos.app.service;

import com.pos.app.dao.UserDao;
import com.pos.app.exception.ApiException;
import com.pos.app.pojo.UserPojo;
import com.pos.app.util.EmailChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserSignUpService {
    @Autowired
    private UserDao ud;

    @Transactional
    public void insert(UserPojo up) throws ApiException {
        UserPojo t_up = ud.select(up.getEmail());
        if (t_up != null) {
            throw new ApiException("User with given email already exists");
        }
        if(EmailChecker.checkAdminEmail(up.getEmail())){
            up.setRole("admin");
        }
        ud.insert(up);
    }
    @Transactional
    public UserPojo get(int id){
        return ud.select(id);
    }
    @Transactional
    public UserPojo get(String email){
        return ud.select(email);
    }

    @Transactional
    public List<UserPojo> getAll(){
        return ud.selectAll();
    }
    // should i keep the functionality of changing the email
    public void update(String email, UserPojo new_up){
        UserPojo up = ud.select(email);
        //up.getEmail(new_up.getEmail());
        up.setPassword(new_up.getPassword());
    }
    @Transactional
    public void delete(int id){
        ud.delete(id);
    }
    @Transactional
    public void delete(String email){
        ud.delete(email);
    }
}
