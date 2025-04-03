package com.ratelimiter;

import com.ratelimiter.service.FixedWindowRateLimiter;
import com.ratelimiter.service.RateLimiter;
import com.ratelimiter.service.SlidingWindowRateLimiter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter fixedRateLimiter = new
                FixedWindowRateLimiter(10,1000);

        RateLimiter slidingWindowRateLimiter = new
                SlidingWindowRateLimiter(10,1000);


        for(int i = 0; i < 12; i++){
            System.out.println("client1 requests processed " + fixedRateLimiter.allowRequest("client-1"));
        }

        for(int i = 0; i < 12; i++){
            System.out.println("client2 requests processed " + slidingWindowRateLimiter.allowRequest("client-2"));
        }
    }
}