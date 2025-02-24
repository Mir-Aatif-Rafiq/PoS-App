package com.pos.app.service;

import com.pos.app.dao.ClientDao;
import com.pos.app.pojo.ClientPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class ClientService {
    @Autowired
    public ClientDao cd;

    @Transactional
    public void insert(ClientPojo cp){
        cd.insert(cp);
    }
    @Transactional
    public ClientPojo get(int id){
        return cd.select(id);
    }
    @Transactional
    public List<ClientPojo> getAll(){
        return cd.selectAll();
    }
    @Transactional
    public void update(int id, ClientPojo new_cp){
        cd.update(id,new_cp);
    }
    @Transactional
    public void delete(int id){
        cd.delete(id);
    }


}
