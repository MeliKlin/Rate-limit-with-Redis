package com.practicing.crudforfun.usecases.customers;

import com.practicing.crudforfun.entities.Customer;
import com.practicing.crudforfun.repositories.CustomersRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class FindCustomerById {

    private CustomersRepository customersRepository;

    public Customer execute(UUID id) {
        return customersRepository.get(id);
    }

}
