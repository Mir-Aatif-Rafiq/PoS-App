package com.pos.app.dto;

import com.pos.app.flow.OrderFlow;
import com.pos.app.model.OrderData;
import com.pos.app.model.OrderDirectoryData;
import com.pos.app.model.OrderForm;
import com.pos.app.pojo.OrderDirectoryPojo;
import com.pos.app.pojo.OrderPojo;
import com.pos.app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDto {

    @Autowired
    private OrderService os;
    @Autowired
    private OrderFlow o_flow;

    public OrderPojo formToPojo(OrderForm of){
        OrderPojo op = new OrderPojo();
        op.setProduct_barcode(of.getProduct_barcode());
        op.setQuantity(of.getQuantity());
        op.setTotal_price(o_flow.getTotal_price(of.getProduct_barcode(), of.getQuantity()));
        op.setClient_id(o_flow.getClient_id(of.getProduct_barcode()));
        return op;
    }
    public OrderData orderPojoToData(OrderPojo op){
        OrderData od = new OrderData();
        od.setId(op.getId());
        od.setName(o_flow.getProductName(op.getProduct_barcode()));
        od.setProduct_barcode(op.getProduct_barcode());
        od.setQuantity(op.getQuantity());
        od.setTotal_price(op.getTotal_price());
        od.setOrder_id(op.getOrder_id());
        od.setClient_id(op.getClient_id());
        od.setCreated_at(op.getCreated_at());
        return od;
    }
    public OrderDirectoryData orderDirectoryPojoToData(OrderDirectoryPojo odp){
        OrderDirectoryData odd = new OrderDirectoryData();
        odd.setOrder_id(odp.getOrder_id());
        odd.setTotal_price(odp.getTotal_price());
        odd.setTotalItems(odp.getTotalItems());
        odd.setCreated_at(odp.getCreated_at());
        return odd;
    }

    public void insert(List<OrderForm> of) {
        OrderDirectoryPojo odp = new OrderDirectoryPojo();
        int total_price = 0;
        int totalItems = of.size();
        odp.setTotalItems(totalItems);
        os.insert(odp);
        for (OrderForm orderForm : of) {
            OrderPojo op = formToPojo(orderForm);
            total_price = op.getTotal_price() + total_price;

            o_flow.reduceInventory(op.getProduct_barcode(),op.getQuantity());
            op.setOrder_id(odp.getOrder_id());
            os.insert(op);
        }
        os.update(total_price,odp);
    }

    public OrderData getOrderById(int id){
        return orderPojoToData(os.getOrderById(id));
    }

    public List<OrderData> getOrdersByOrder_id(int order_id){
        List<OrderPojo> l_op = os.getOrdersByOrder_id(order_id);
        List<OrderData> l_od= new ArrayList<>();
        for(OrderPojo orderPojo : l_op){
            l_od.add(orderPojoToData(orderPojo));
        }
        return l_od;
    }
    public List<OrderData> getAllOrders(){
        List<OrderPojo> l_op = os.getAllOrders();
        List<OrderData> l_od= new ArrayList<>();
        for(OrderPojo orderPojo : l_op){
            l_od.add(orderPojoToData(orderPojo));
        }
        return l_od;
    }
    public OrderDirectoryData get(int id){
        return orderDirectoryPojoToData(os.get(id));
    }


    public List<OrderDirectoryData> getAll(){
        List<OrderDirectoryPojo> l_op = os.getAll();
        List<OrderDirectoryData> l_odd = new ArrayList<>();
        for(OrderDirectoryPojo odp : l_op){
            OrderDirectoryData odd = orderDirectoryPojoToData(odp);
            l_odd.add(odd);
        }
        return l_odd;
    }
}
