package com.pos.app.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Clients", uniqueConstraints = {
    @UniqueConstraint(columnNames = "client_name", name = "uk_client_name")
})
@Setter
@Getter
public class ClientPojo extends AbstractPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int clientId;
    
    @NotBlank(message = "Client name cannot be blank")
    @Size(min = 2, max = 100, message = "Client name must be between 2 and 100 characters")
    @Column(name = "client_name")
    private String clientName;
}

