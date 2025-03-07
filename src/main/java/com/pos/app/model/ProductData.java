package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class ProductData extends ProductForm {
    private int productId;
    private String clientName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
