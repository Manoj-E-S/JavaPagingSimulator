package io.github.manoj_e_s.java_page_simulator.backend_components;

public class CacheConfig {

    // Size of a page in KB
    public int pageSizeInKb;

    // No. of Frames in the cache
    public int framesInCache;

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


    // No Params Constructor
    public CacheConfig() {
    }

    // All Params Constructor
    public CacheConfig(
            boolean measurePerformance,
            int cacheMissTimeIntervalInSeconds,
            int cacheHitTimeIntervalInSeconds,
            int framesInCache,
            int pageSizeInKb
    ) {
        this.measurePerformance = measurePerformance;
        this.cacheMissTimeIntervalInSeconds = cacheMissTimeIntervalInSeconds;
        this.cacheHitTimeIntervalInSeconds = cacheHitTimeIntervalInSeconds;
        this.framesInCache = framesInCache;
        this.pageSizeInKb = pageSizeInKb;
    }


    // METHODS
    @Override
    public String toString() {
        return "GlobalConfig {\n" +
                "\tpageSize = " + pageSizeInKb + "kB,\n" +
                "\tframesInCache = " + framesInCache + ",\n" +
                "\tcacheHitTimeInterval = " + cacheHitTimeIntervalInSeconds + "s,\n" +
                "\tcacheMissTimeInterval = " + cacheMissTimeIntervalInSeconds + "s,\n" +
                "\tmeasurePerformance = " + measurePerformance + "\n" +
                '}';
    }
}
