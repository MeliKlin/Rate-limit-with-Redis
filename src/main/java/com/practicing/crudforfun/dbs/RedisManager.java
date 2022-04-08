package com.practicing.crudforfun.dbs;

import lombok.Getter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Getter
public class RedisManager implements DatabaseManager<RedissonClient> {

    private RedissonClient redissonClient;

    public RedisManager() {
        connect();
    }

    private void connect() {
        String nodeAddress = String.format("redis://%s:%s", "localhost", 7000);

        Config config = new Config();
        config.useSingleServer().setAddress(nodeAddress).setTimeout(3000);

        this.redissonClient = Redisson.create(config);
    }

    public RedissonClient getConnection() {
        return redissonClient;
    }

}
