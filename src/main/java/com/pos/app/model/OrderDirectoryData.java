package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class OrderDirectoryData {

    private int order_id;
    private int total_price;
    private int totalItems;
    private ZonedDateTime created_at;
}
