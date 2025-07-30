package com.myapp.desk.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    // Spring Boot auto-configuration handles Redis cache setup
    // No additional configuration needed for basic Redis caching
}
