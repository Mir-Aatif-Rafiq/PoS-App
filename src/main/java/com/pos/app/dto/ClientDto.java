package com.pos.app.dto;

import com.pos.app.model.ClientData;
import com.pos.app.model.ClientForm;
import com.pos.app.pojo.ClientPojo;
import com.pos.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClientDto {
    
    @Autowired
    private ClientService cs;

    public ClientPojo formToPojo(ClientForm cf){
        ClientPojo cp = new ClientPojo();
        cp.setClient_name(cf.getName());
        return cp;
    }
    public ClientData pojoToData(ClientPojo cp){
        ClientData cd = new ClientData();
        cd.setName(cp.getClient_name());
        cd.setId(cp.getClient_id());
        cd.setCreated_at(cp.getCreated_at());
        cd.setUpdated_at(cp.getUpdated_at());
        cd.setDeleted_at(cp.getDeleted_at());
        return cd;
    }

    public void insert(ClientForm cf){
        cs.insert(formToPojo(cf));
    }

    public ClientData get(int id){
        return pojoToData(cs.get(id));
    }

    public List<ClientData> getAll(){
        List<ClientPojo> l_cp = cs.getAll();
        List<ClientData> l_cd = new ArrayList<>();
        for(ClientPojo cp : l_cp){
            ClientData pd = pojoToData(cp);
            l_cd.add(pd);
        }
        return l_cd;
    }
    public void update(int id, ClientForm new_pf){
        cs.update(id,formToPojo(new_pf));
    }
    public void delete(int id){
        cs.delete(id);
    }
}
