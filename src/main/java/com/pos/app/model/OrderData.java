package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderData extends OrderForm {
    private int id;
    private String name;
    private int totalPrice;
    private int clientId;
    private int orderId;
    private ZonedDateTime createdAt;
}
