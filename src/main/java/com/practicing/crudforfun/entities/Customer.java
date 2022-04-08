package com.practicing.crudforfun.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

    private UUID id;
    private String first_name;
    private String last_name;
    private String document;
    private Date created_at;
    private Date updated_at;

    public Customer (String first_name, String last_name, String document) {
        id = UUID.randomUUID();
        this.first_name = first_name;
        this.last_name = last_name;
        this.document = document;
        created_at = new Date();
        updated_at = new Date();
    }

}
