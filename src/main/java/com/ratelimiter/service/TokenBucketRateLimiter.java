package com.ratelimiter.service;

import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {
    private final int maxBucketSize;
    private final long refillRateInMillis;  //interval to add the token

    public TokenBucketRateLimiter(int maxBucketSize, long refillRateInMillis) {
        this.maxBucketSize = maxBucketSize;
        this.refillRateInMillis = refillRateInMillis;
    }

    ConcurrentHashMap<String,Integer> tokenPerClient = new ConcurrentHashMap<>();
    ConcurrentHashMap<String,Long> lastRefillTimePerClient = new ConcurrentHashMap<>();

    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();

       /* System.out.println("Tokens for client in hashMap" + clientId + ": " + tokenPerClient.get(clientId));*/
        tokenPerClient.putIfAbsent(clientId, maxBucketSize);
        lastRefillTimePerClient.putIfAbsent(clientId, currentTime);

        synchronized (this) {
            int tokens = tokenPerClient.get(clientId);
            long lastRefillTime = lastRefillTimePerClient.get(clientId);

            long timeSinceLastRefill = currentTime - lastRefillTime;
            int tokenToAdd = (int) (timeSinceLastRefill / refillRateInMillis);

            if (tokenToAdd > 0) {
                tokens = Math.min(tokens + tokenToAdd, maxBucketSize); // so that it will not exceed the maxBucketSize.
                lastRefillTimePerClient.put(clientId, currentTime);
                tokenPerClient.put(clientId, tokens);
            }

            System.out.println("Tokens for client " + clientId + ": " + tokens);

            if (tokens > 0) {
                tokenPerClient.put(clientId, tokens - 1);
                return true;
            }
        }
        return false;
    }
}
