package com.ratelimiter;

import com.ratelimiter.service.LeakyBucketRateLimiter;

public class LeakyBucketTesting {
    public static void main(String[] args) throws InterruptedException {
        LeakyBucketRateLimiter rateLimiter =
                new LeakyBucketRateLimiter(3,1000);

        // Test 1 - Make 3 requests (bucket should allow all, as the bucket isn't full)
        System.out.println("Test 1 - Initial Requests for leaky bucket:");
        for (int i = 0; i < 3; i++) {
            System.out.println("Request " + (i + 1) + ": " + rateLimiter.allowRequest("clientId"));  // Should return true
        }

        // Test 2: 4th request should be denied because the bucket is full
        System.out.println("Test 2 - Exceeding the bucket:");
        System.out.println("Request 4: " + rateLimiter.allowRequest("clientId"));  // Should return false


        // Test 3: Wait for 2 seconds (bucket should leak 2 requests)
        System.out.println("Test 3 - Wait for 2 seconds:");
        Thread.sleep(2000);

        // After 2 seconds, the bucket should have space for 2 more requests
        for (int i = 0; i < 2; i++) {
            System.out.println("Request " + (i + 4) + ": " + rateLimiter.allowRequest("clientId"));  // Should return true
        }

        // Test 4: Another request should be denied because the bucket is full again
        System.out.println("Test 4 - Bucket full again:");
        System.out.println("Request 7: " + rateLimiter.allowRequest("clientId"));  // Should return false

    }
}