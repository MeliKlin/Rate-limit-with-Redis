package com.practicing.crudforfun.repositories;

import com.practicing.crudforfun.dbs.DatabaseManager;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;

import java.util.Collections;

public class RateLimitRepository implements CacheRepository<String, Integer> {

    private final RedissonClient redissonClient;

    public RateLimitRepository(DatabaseManager<RedissonClient> dbManager) {
        this.redissonClient = dbManager.getConnection();
    }

    @Override
    public void set(String key, Integer tokensLeft) {
        String value = String.valueOf(tokensLeft);

        redissonClient.getScript(StringCodec.INSTANCE)
                .eval(RScript.Mode.READ_WRITE, "redis.call(\"set\", KEYS[1], ARGV[1], \"EX\", ARGV[2])", RScript.ReturnType.VALUE, Collections.singletonList(key), value, 10);
    }

    @Override
    public Integer get(String key) {
        String result = redissonClient.getScript(StringCodec.INSTANCE)
                .eval(RScript.Mode.READ_ONLY, "return redis.call(\"get\", KEYS[1])", RScript.ReturnType.VALUE, Collections.singletonList(key));

        if (result == null) {
            return -1;
        }

        return Integer.valueOf(result);
    }
}
