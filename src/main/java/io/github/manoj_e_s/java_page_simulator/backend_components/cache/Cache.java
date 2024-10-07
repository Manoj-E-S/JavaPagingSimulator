package io.github.manoj_e_s.java_page_simulator.backend_components.cache;

import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy.CachingPolicy;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.PerformanceMetrics;

import java.util.HashMap;

// Singleton Cache
public class Cache {

    // DATA MEMBERS ----------------------------------------------------------------------------------------------------
    // Caching Policy
    public static CachingPolicy cachingPolicy;

    // Frames K: pageName, V: Page
    private final HashMap<String, Page> frames;

    // Cache Config
    private static CacheConfig cacheConfig;

    // Performance Metrics
    private static PerformanceMetrics performanceMetrics;


    // INSTANCE --------------------------------------------------------------------------------------------------------
    private static Cache instance = null;


    // CONSTRUCTORS ----------------------------------------------------------------------------------------------------
    // GlobalConfig PARAM CONSTRUCTOR
    private Cache(CacheConfig cc) {
        Cache.cacheConfig = cc;
        Cache.performanceMetrics = new PerformanceMetrics();
        this.frames = new HashMap<>();
    }


    // GETTERS AND SETTERS ---------------------------------------------------------------------------------------------
    public static CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    public static CachingPolicy getCachingPolicy() {
        return cachingPolicy;
    }

    public static void setCachingPolicy(CachingPolicy cachingPolicy) {
        Cache.cachingPolicy = cachingPolicy;
    }

    public static PerformanceMetrics getPerformanceMetrics() {
        return Cache.performanceMetrics;
    }

    // INSTANCE RELATED METHODS ----------------------------------------------------------------------------------------
    // Instantiate
    public static void instantiate(CacheConfig cacheConfig) {
        Cache.instance = new Cache(cacheConfig);
    }

    // Get Instance
    public static synchronized Cache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cache has not been initialized with GlobalConfig yet.\nUse Cache.instantiate(globalConfig)");
        }
        return instance;
    }

    // Unmount the Cache
    public static synchronized void unmount() {
        instance = null;
        cacheConfig = null;
        performanceMetrics = null;
    }


    // METHODS  --------------------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Cache {\n" +
                "\tnumOfFrames = " + cacheConfig.getFramesInCache() + ",\n" +
                "\tcachingPolicy = " + cachingPolicy + ",\n" +
                "\tframes = " + frames + "\n" +
                '}';
    }

    // Is Cache Full?
    public boolean isFull() {
        return this.frames.size() >= cacheConfig.getFramesInCache();
    }

    // How many page-slots are occupied in the Cache?
    public int getOccupiedPageSlots() {
        return this.frames.size();
    }

    // Put page
    public void put(Page page) {
        if(page == null) {
            throw new IllegalStateException("Page cannot be NULL while putting into the Cache");
        }
        this.frames.put(page.getPageName(), page);

        Cache.performanceMetrics.incrementMemoryByPageSize();
        Cache.performanceMetrics.recordMemoryUsage("Insert Page(" + page.getPageName() + ')');
    }

    // Get page
    public Page get(String pageName) {
        Cache.performanceMetrics.startTimer();

        Page requiredPage = this.frames.get(pageName);
        if(requiredPage != null) {
            // Cache Hit
            Cache.performanceMetrics.recordHit();

            cachingPolicy.handleHit(requiredPage);

            Cache.performanceMetrics.stopTimer();
            return requiredPage;
        }

        // Cache Miss
        Cache.performanceMetrics.recordMiss();

        // Add required page to cache by Policy and get it
        requiredPage = cachingPolicy.handleMiss(pageName);

        Cache.performanceMetrics.stopTimer();
        return requiredPage;
    }

    // Evict page
    public void evict(String pageName) {
        this.frames.remove(pageName);
        Cache.performanceMetrics.recordEviction();

        Cache.performanceMetrics.decrementMemoryByPageSize();
        Cache.performanceMetrics.recordMemoryUsage("Evict Page(" + pageName + ')');
    }

}
