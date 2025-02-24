package com.pos.app.dto;

import com.pos.app.exception.ApiException;
import com.pos.app.model.UserSignUpData;
import com.pos.app.model.UserSignUpForm;
import com.pos.app.pojo.UserPojo;
import com.pos.app.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserSignUpDto {
    @Autowired
    private UserSignUpService usus;

    public UserPojo formToPojo(UserSignUpForm usuf) {
        UserPojo up = new UserPojo();
        up.setEmail(usuf.getEmail());
        up.setPassword(usuf.getPassword());

        return up;
    }
    public UserSignUpData pojoToData(UserPojo up){
        UserSignUpData usud = new UserSignUpData();
        usud.setEmail(up.getEmail());
        usud.setId(up.getId());
        usud.setPassword(up.getPassword());
        usud.setRole(up.getRole());
        return usud;
    }
    public void insert(UserSignUpForm usuf) throws ApiException {
        usus.insert(formToPojo(usuf));
    }
    public UserSignUpData get(int id){
        return pojoToData(usus.get(id));
    }

    public UserSignUpData get(String email){
        return pojoToData(usus.get(email));
    }
    public List<UserSignUpData> getAll(){
        List<UserPojo> l_up = usus.getAll();
        List<UserSignUpData> l_usud = new ArrayList<>();
        for(UserPojo up : l_up){
            UserSignUpData pd = pojoToData(up);
            l_usud.add(pd);
        }
        return l_usud;
    }
    public void update(String email, UserSignUpForm new_pf){
        usus.update(email,formToPojo(new_pf));
    }

    public void delete(int id){
        usus.delete(id);
    }

    public void delete(String email){
        usus.delete(email);
    }

}
