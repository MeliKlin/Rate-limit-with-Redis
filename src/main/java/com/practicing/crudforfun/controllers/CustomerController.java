package com.practicing.crudforfun.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicing.crudforfun.dtos.CreateCustomerDTO;
import com.practicing.crudforfun.dbs.RedisManager;
import com.practicing.crudforfun.entities.Customer;
import com.practicing.crudforfun.repositories.CustomersRepository;
import com.practicing.crudforfun.usecases.customers.CreateCustomer;
import com.practicing.crudforfun.usecases.customers.FindCustomerById;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final RedisManager redisManager;
    private final ObjectMapper objectMapper;
    private final CustomersRepository customersRepository;

    public CustomerController() {
        redisManager = new RedisManager();
        objectMapper = new ObjectMapper();
        this.customersRepository = new CustomersRepository(redisManager, objectMapper);
    }

    @PostMapping
    public void createCustomer(
            @RequestBody CreateCustomerDTO customerDTO
    ) {
        if (customerDTO.isValid()) {
            new CreateCustomer(customersRepository).execute(customerDTO);
        }
    }

    @GetMapping("/{id}")
    public Customer findCustomerById(@PathVariable UUID id) {
        return new FindCustomerById(customersRepository).execute(id);
    }

}
