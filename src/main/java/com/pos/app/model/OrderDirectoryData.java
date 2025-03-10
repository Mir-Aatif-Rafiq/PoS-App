package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class OrderDirectoryData {

    private Integer orderId;
    private Double totalPrice;
    private Integer totalItems;
    private ZonedDateTime createdAt;
}
