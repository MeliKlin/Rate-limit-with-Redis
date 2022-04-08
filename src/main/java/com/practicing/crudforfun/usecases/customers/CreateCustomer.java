package com.practicing.crudforfun.usecases.customers;

import com.practicing.crudforfun.dtos.CreateCustomerDTO;
import com.practicing.crudforfun.entities.Customer;
import com.practicing.crudforfun.repositories.CustomersRepository;

public class CreateCustomer {

    private final CustomersRepository customersRepository;

    public CreateCustomer(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public void execute(CreateCustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getFirst_name(), customerDTO.getLast_name(), customerDTO.getDocument());

        customersRepository.set(customer.getId(), customer);
    }

}
