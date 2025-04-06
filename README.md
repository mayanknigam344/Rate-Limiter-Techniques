# Java Rate Limiter Implementations 🚦

This project contains implementations of various rate limiting algorithms in Java. It's designed for learning and experimentation with different rate limiting techniques used in distributed systems and APIs.

## 📁 Project Structure

```
src/main/java/com/ratelimiter/
├── service/
│   ├── FixedWindowRateLimiter.java
│   ├── LeakyBucketRateLimiter.java
│   ├── SlidingWindowRateLimiter.java
│   ├── TokenBucketRateLimiter.java
│   ├── RateLimiter.java            # Interface or base class
│
├── LeakyBucketTesting.java        # Test driver for Leaky Bucket
├── TokenBucketTesting.java        # Test driver for Token Bucket
├── Main.java                      # Entry point or test runner
```

## 🚀 Rate Limiter Algorithms Included

### ✅ Fixed Window Rate Limiter
Allows a certain number of requests per fixed time window (e.g., 100 requests per minute).

### 💧 Leaky Bucket Rate Limiter
Processes requests at a constant rate, storing overflow in a queue. Simulates a bucket leaking at a constant rate.

### 📊 Sliding Window Rate Limiter
Divides a time window into smaller segments for more accurate limiting.

### 🪙 Token Bucket Rate Limiter
Tokens are added at a fixed rate. Requests consume tokens if available, allowing bursts.

## 🔬 How to Test

Each limiter has a corresponding test class:
- Run `Main.java` to interact with different implementations.
- `LeakyBucketTesting.java` or `TokenBucketTesting.java` can be executed to observe behaviors of specific limiters.

## 📦 Build & Run

This is a Maven-based project.

### Prerequisites:
- Java 11+
- Maven 3.x

### Build the project:
```bash
mvn clean install
```

## 🧠 Concepts Covered

- Thread safety
- Time-based scheduling
- Request throttling
- System design scalability

