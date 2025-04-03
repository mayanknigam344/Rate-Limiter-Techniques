package com.ratelimiter.service;

public interface RateLimiter {
    public boolean allowRequest(String clientId);
}
