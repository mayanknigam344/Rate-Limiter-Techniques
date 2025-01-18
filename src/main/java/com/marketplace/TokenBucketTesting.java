package com.marketplace;

import com.marketplace.service.RateLimiter;
import com.marketplace.service.TokenBucketRateLimiter;

public class TokenBucketTesting {
    public static void main(String[] args) throws InterruptedException {
        RateLimiter tokenBucketRateLimiter =
                new TokenBucketRateLimiter(3,1000);

        // Test 1: Make 3 requests immediately (bucket size = 3)
        // requests processed
        System.out.println("Test 1 - Initial Requests:");
        for(int i = 0; i < 3; i++){
            System.out.println("Request " + (i + 1) + ": " + tokenBucketRateLimiter.allowRequest("clientId"));
        }

        // Test 2: Make one more request (bucket should be empty)
        System.out.println("Test 2 - Exceeding the bucket:");
        System.out.println("Request 4: " + tokenBucketRateLimiter.allowRequest("clientId"));  // Should be false (bucket empty)


        // Test 3: Wait for 2 seconds (enough time for 2 tokens to refill)
        System.out.println("Test 3 - Wait for 2 seconds for refill:");
        Thread.sleep(2000);

        // Make 2 more requests (should succeed, as 2 tokens were refilled)
        for (int i = 0; i < 2; i++) {
            System.out.println("Request " + (i + 5) + ": " + tokenBucketRateLimiter.allowRequest("clientId"));  // Should be true
        }

        // Test 4: Make one more request (bucket should be empty again)
        System.out.println("Test 4 - Exhaust after refill:");
        System.out.println("Request 7: " + tokenBucketRateLimiter.allowRequest("clientId"));  // Should be false

        // Test 5: Wait for full refill (3 tokens should be refilled)
        System.out.println("Test 5 - Wait for full refill (3 seconds):");
        Thread.sleep(3000);

        // Now, we should be able to make 3 more requests
        for (int i = 0; i < 3; i++) {
            System.out.println("Request " + (i + 8) + ": " + tokenBucketRateLimiter.allowRequest("clientId"));  // Should be true
        }

        // Test 6: No tokens left, should be denied
        System.out.println("Test 6 - Bucket exhausted again:");
        System.out.println("Request 11: " + tokenBucketRateLimiter.allowRequest("clientId"));  // Should be false
    }
}