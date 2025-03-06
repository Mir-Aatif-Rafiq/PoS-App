package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class OrderData extends OrderForm{
    private int id;
    private String name;
    private int total_price;
    private int client_id;
    private int order_id;
    private ZonedDateTime created_at;
}
