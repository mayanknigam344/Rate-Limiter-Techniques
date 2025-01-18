package com.marketplace.service;

import java.util.HashMap;

public class FixedWindowRateLimiter implements RateLimiter {

    private final int maxRequests;
    private final int windowSizeInMillis;

    public FixedWindowRateLimiter(int maxRequests, int windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    HashMap<String,Integer> requestCounts = new HashMap<>();
    HashMap<String,Long> windowStartTimes = new HashMap<>();

    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();
        requestCounts.putIfAbsent(clientId,0);
        windowStartTimes.putIfAbsent(clientId,currentTime);

        long windowStartTime = windowStartTimes.get(clientId);
        if(currentTime - windowStartTime >= windowSizeInMillis) {
            windowStartTimes.put(clientId,currentTime);
            // resetting the window
            requestCounts.put(clientId,0);
        }

        int requestCount = requestCounts.get(clientId);
        if(requestCount<maxRequests) {
            requestCounts.put(clientId,requestCount+1);
            return true;
        }

        return false;
    }
}
