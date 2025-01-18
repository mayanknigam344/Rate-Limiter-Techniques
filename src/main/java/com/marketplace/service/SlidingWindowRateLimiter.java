package com.marketplace.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    HashMap<String, Queue<Long>> requestTimeStamps = new HashMap<>();

    @Override
    public boolean allowRequest(String clientId) {
        long currentTime = System.currentTimeMillis();
        requestTimeStamps.putIfAbsent(clientId, new LinkedList<>());

        Queue<Long> timeStamps = requestTimeStamps.get(clientId);

        while(!timeStamps.isEmpty() && currentTime - timeStamps.peek() > windowSizeInMillis) {
            timeStamps.poll();
        }
        int requests = timeStamps.size();
        if(requests < maxRequests) {
            timeStamps.add(currentTime);
            return true;
        }
        return false;
    }
}
