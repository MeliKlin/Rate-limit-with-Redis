package com.practicing.crudforfun.controllers;

import com.practicing.crudforfun.dbs.DatabaseManager;
import com.practicing.crudforfun.dbs.RedisManager;
import com.practicing.crudforfun.ratelimits.TokenBucket;
import com.practicing.crudforfun.repositories.RateLimitRepository;
import com.practicing.crudforfun.utils.GetClientIp;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HealthCheckController {

    private final DatabaseManager<RedissonClient> redisManager;
    private final RateLimitRepository rateLimitRepository;

    public HealthCheckController() {
        this.redisManager = new RedisManager();
        this.rateLimitRepository = new RateLimitRepository(redisManager);
    }

    @GetMapping("/get-ip")
    public String getIp(HttpServletRequest request) {
        return new GetClientIp().run(request);
    }

    @GetMapping("/rate-limit")
    public String rateLimit(HttpServletRequest request) {
        String clientIp = new GetClientIp().run(request);

        int tokensLeft = new TokenBucket(rateLimitRepository)
                .execute(clientIp, "rate-limit");

        if (tokensLeft == 0) {
            return "Calma jovem!!\n 429 - Too many requests";
        }
        return "Tokens left: " + tokensLeft;
    }

}
