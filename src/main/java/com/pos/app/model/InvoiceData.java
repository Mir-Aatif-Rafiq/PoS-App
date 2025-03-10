package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class InvoiceData {
    private Integer id;
    private String path;
    private ZonedDateTime createdAt;
}
