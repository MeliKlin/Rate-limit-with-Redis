package com.practicing.crudforfun.ratelimits;

import com.practicing.crudforfun.repositories.CacheRepository;

public class TokenBucket {

    CacheRepository<String, Integer> cacheRepository;

    public TokenBucket(CacheRepository<String, Integer> cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public int execute(String ip, String path) {
        String redisKey = ip.concat(" - ").concat(path);

        Integer tokensLeft = cacheRepository.get(redisKey);

        if (tokensLeft.equals(-1)) {
            cacheRepository.set(redisKey, 20);
            return 20;
        }

        if (tokensLeft > 0) {
            tokensLeft--;
            cacheRepository.set(redisKey, tokensLeft);
            return tokensLeft;
        }

        return 0;
    }

}
