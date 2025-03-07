package com.pos.app.model;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;

@Setter
@Getter
public class ClientData extends ClientForm {
    private int id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
