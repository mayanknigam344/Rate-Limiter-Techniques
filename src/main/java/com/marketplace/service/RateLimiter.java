package com.marketplace.service;

public interface RateLimiter {
    public boolean allowRequest(String clientId);
}
