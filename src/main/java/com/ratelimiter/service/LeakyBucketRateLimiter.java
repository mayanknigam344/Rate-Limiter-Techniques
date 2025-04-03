package com.ratelimiter.service;

import java.util.HashMap;

public class LeakyBucketRateLimiter implements RateLimiter {

    private final int maxBucketSize;
    private final long leakRateInMillis; // rate at which requests are processed. i.e one request in 1 second(1000 millis)

    public LeakyBucketRateLimiter(int maxBucketSize, long leakRateInMillis) {
        this.maxBucketSize = maxBucketSize;
        this.leakRateInMillis = leakRateInMillis;
    }

    HashMap<String,Integer> currentBucketSizePerClient = new HashMap<>();
    HashMap<String, Long > lastRequestTimePerClient = new HashMap<>();


    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();

        currentBucketSizePerClient.putIfAbsent(clientId,0);
        lastRequestTimePerClient.putIfAbsent(clientId,currentTime);

        int currentBucketSize = currentBucketSizePerClient.get(clientId);
        long lastRequestTime = lastRequestTimePerClient.get(clientId);

        long timeSinceLastRequest = currentTime - lastRequestTime;
        int leakedRequests = (int) (timeSinceLastRequest / leakRateInMillis);

        if(leakedRequests > 0){
            //System.out.println("Leaked " + leakedRequests + " requests per second");
            currentBucketSize = Math.max(0, currentBucketSize - leakedRequests);
            lastRequestTimePerClient.put(clientId,currentTime);
        }

        if(currentBucketSize < maxBucketSize){
            currentBucketSizePerClient.put(clientId,currentBucketSize + 1);
            return true;
        }
        return false;
    }
}
