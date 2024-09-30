package io.github.manoj_e_s.java_page_simulator.backend_components;

import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.CachingPolicy;


public class GlobalConfig {

    // Size of a page in KB
    public int pageSizeInKb;

    // No. of Frames in the cache
    public int framesInCache;

    // Caching Policy in Use
    public final CachingPolicy cachingPolicy;

    // time values
    public int cacheHitTimeIntervalInSeconds;
    public int cacheMissTimeIntervalInSeconds;

    // Should Performance be measured?
    public boolean measurePerformance;


    // Getters and Setters
    public int getPageSizeInKb() {
        return pageSizeInKb;
    }

    public void setPageSizeInKb(int pageSizeInKb) {
        this.pageSizeInKb = pageSizeInKb;
    }

    public int getFramesInCache() {
        return framesInCache;
    }

    public void setFramesInCache(int framesInCache) {
        this.framesInCache = framesInCache;
    }

    public CachingPolicy getCachingPolicy() {
        return cachingPolicy;
    }

    public int getCacheHitTimeIntervalInSeconds() {
        return cacheHitTimeIntervalInSeconds;
    }

    public void setCacheHitTimeIntervalInSeconds(int cacheHitTimeIntervalInSeconds) {
        this.cacheHitTimeIntervalInSeconds = cacheHitTimeIntervalInSeconds;
    }

    public int getCacheMissTimeIntervalInSeconds() {
        return cacheMissTimeIntervalInSeconds;
    }

    public void setCacheMissTimeIntervalInSeconds(int cacheMissTimeIntervalInSeconds) {
        this.cacheMissTimeIntervalInSeconds = cacheMissTimeIntervalInSeconds;
    }

    public boolean isMeasurePerformance() {
        return measurePerformance;
    }

    public void setMeasurePerformance(boolean measurePerformance) {
        this.measurePerformance = measurePerformance;
    }


    // All Params Constructor
    public GlobalConfig(
            boolean measurePerformance,
            int cacheMissTimeIntervalInSeconds,
            int cacheHitTimeIntervalInSeconds,
            CachingPolicy cachingPolicy,
            int framesInCache,
            int pageSizeInKb
    ) {
        this.measurePerformance = measurePerformance;
        this.cacheMissTimeIntervalInSeconds = cacheMissTimeIntervalInSeconds;
        this.cacheHitTimeIntervalInSeconds = cacheHitTimeIntervalInSeconds;
        this.cachingPolicy = cachingPolicy;
        this.framesInCache = framesInCache;
        this.pageSizeInKb = pageSizeInKb;
    }


    // METHODS
    @Override
    public String toString() {
        return "GlobalConfig {\n" +
                "\tpageSize = " + pageSizeInKb + "kB,\n" +
                "\tframesInCache = " + framesInCache + ",\n" +
                "\tcachingPolicy = " + cachingPolicy + ",\n" +
                "\tcacheHitTimeInterval = " + cacheHitTimeIntervalInSeconds + "s,\n" +
                "\tcacheMissTimeInterval = " + cacheMissTimeIntervalInSeconds + "s,\n" +
                "\tmeasurePerformance = " + measurePerformance + "\n" +
                '}';
    }
}
