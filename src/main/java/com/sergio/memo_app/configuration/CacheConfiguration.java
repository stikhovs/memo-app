package com.sergio.memo_app.configuration;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

//@Configuration
//@EnableCaching
public class CacheConfiguration {

    //@Bean
    public ConcurrentMapCacheManager concurrentMapCacheManager() {
        var cacheManager = new ConcurrentMapCacheManager();
        cacheManager.setAllowNullValues(true);
        cacheManager.setStoreByValue(true);
        return cacheManager;
    }

}
