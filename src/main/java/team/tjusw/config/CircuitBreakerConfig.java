package team.tjusw.config;
import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;

@Configuration
public class CircuitBreakerConfig {
	
	
    @Bean
    public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
            CircuitBreakerRegistry circuitBreakerRegistry,
            TimeLimiterRegistry timeLimiterRegistry) {
        ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory = new ReactiveResilience4JCircuitBreakerFactory(circuitBreakerRegistry, timeLimiterRegistry);
        reactiveResilience4JCircuitBreakerFactory.configureCircuitBreakerRegistry(circuitBreakerRegistry);
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(20)).cancelRunningFuture(true)
                .build();
        reactiveResilience4JCircuitBreakerFactory.configure(builder -> builder.timeLimiterConfig(timeLimiterConfig).build(),
        		"globalCircuitBreaker",
        		"userCircuitBreaker",
        		"businessCircuitBreaker",
        		"foodCircuitBreaker",
        		"cartCircuitBreaker",
        		"orderCircuitBreaker",
        		"deliveryCircuitBreaker",
        		"walletCircuitBreaker",
        		"pointCircuitBreaker");
        return reactiveResilience4JCircuitBreakerFactory;
    }
}