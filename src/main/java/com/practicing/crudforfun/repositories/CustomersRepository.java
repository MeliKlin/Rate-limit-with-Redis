package com.practicing.crudforfun.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practicing.crudforfun.dbs.DatabaseManager;
import com.practicing.crudforfun.entities.Customer;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class CustomersRepository implements ApplicationRepository<UUID, Customer> {

    private final RedissonClient redissonClient;
    private final ObjectMapper objectMapper;

    public CustomersRepository(DatabaseManager<RedissonClient> redissonClient, ObjectMapper objectMapper) {
        this.redissonClient = redissonClient.getConnection();
        this.objectMapper = objectMapper;
    }

    @Override
    public void set(UUID id, Customer customer) {
        String key = String.valueOf(id);
        String value = null;
        try {
            value = objectMapper.writeValueAsString(customer);
        } catch (JsonProcessingException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        redissonClient.getScript(StringCodec.INSTANCE)
                .eval(RScript.Mode.READ_WRITE, "redis.call(\"set\", KEYS[1], ARGV[1])", RScript.ReturnType.VALUE, Collections.singletonList(key), value);
    }

    @Override
    public Customer get(UUID id) {
        String key = String.valueOf(id);
        Customer customer = new Customer();

        String result = redissonClient.getScript(StringCodec.INSTANCE)
                .eval(RScript.Mode.READ_ONLY, "return redis.call(\"get\", KEYS[1])", RScript.ReturnType.VALUE, Collections.singletonList(key));
        if (result == null) {
            return customer;
        }

        try {
            customer = objectMapper.readValue(result, Customer.class);
        } catch (JsonProcessingException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return customer;
    }

}
