package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderData extends OrderForm {
    private Integer id;
    private String name;
    private Double totalPrice;
    private Integer clientId;
    private Integer orderId;
    private ZonedDateTime createdAt;
}
