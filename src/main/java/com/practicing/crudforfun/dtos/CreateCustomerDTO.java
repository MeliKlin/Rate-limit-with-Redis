package com.practicing.crudforfun.dtos;

import lombok.Data;

@Data
public class CreateCustomerDTO {

    private String first_name;
    private String last_name;
    private String document;

    public boolean isValid() {
        if (first_name.equals("")) {
            System.out.println("First name is required");
            return false;
        }
        if (last_name.equals("")) {
            System.out.println("Last name is required");
            return false;
        }
        if (document.equals("")) {
            System.out.println("Document is required");
            return false;
        }
        return true;
    }

}
