package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Setter
@Getter
public class ClientData extends ClientForm {
    private Integer id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
