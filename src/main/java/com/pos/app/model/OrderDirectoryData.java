package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class OrderDirectoryData {

    private int orderId;
    private int totalPrice;
    private int totalItems;
    private ZonedDateTime createdAt;
}
