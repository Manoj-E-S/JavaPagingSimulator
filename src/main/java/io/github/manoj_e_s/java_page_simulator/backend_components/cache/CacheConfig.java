package io.github.manoj_e_s.java_page_simulator.backend_components.cache;

public class CacheConfig {

    // Size of a page in KB
    public int pageSizeInBytes;

    // No. of Frames in the cache
    public int framesInCache;

    // time values
    public int cacheHitTimeIntervalInMillis;
    public int cacheMissTimeIntervalInMillis;

    // Should Performance be measured?
    public boolean measurePerformance;


    // Getters and Setters
    public int getPageSizeInBytes() {
        return pageSizeInBytes;
    }

    public void setPageSizeInBytes(int pageSizeInBytes) {
        this.pageSizeInBytes = pageSizeInBytes;
    }

    public int getFramesInCache() {
        return framesInCache;
    }

    public void setFramesInCache(int framesInCache) {
        this.framesInCache = framesInCache;
    }

    public int getCacheHitTimeIntervalInMillis() {
        return cacheHitTimeIntervalInMillis;
    }

    public void setCacheHitTimeIntervalInMillis(int cacheHitTimeIntervalInMillis) {
        this.cacheHitTimeIntervalInMillis = cacheHitTimeIntervalInMillis;
    }

    public int getCacheMissTimeIntervalInMillis() {
        return cacheMissTimeIntervalInMillis;
    }

    public void setCacheMissTimeIntervalInMillis(int cacheMissTimeIntervalInMillis) {
        this.cacheMissTimeIntervalInMillis = cacheMissTimeIntervalInMillis;
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
            int cacheMissTimeIntervalInMillis,
            int cacheHitTimeIntervalInMillis,
            int framesInCache,
            int pageSizeInBytes
    ) {
        this.measurePerformance = measurePerformance;
        this.cacheMissTimeIntervalInMillis = cacheMissTimeIntervalInMillis;
        this.cacheHitTimeIntervalInMillis = cacheHitTimeIntervalInMillis;
        this.framesInCache = framesInCache;
        this.pageSizeInBytes = pageSizeInBytes;
    }


    // METHODS
    @Override
    public String toString() {
        return "CacheConfig {\n" +
                "\tpageSize = " + pageSizeInBytes + "kB,\n" +
                "\tframesInCache = " + framesInCache + ",\n" +
                "\tcacheHitTimeInterval = " + cacheHitTimeIntervalInMillis + "s,\n" +
                "\tcacheMissTimeInterval = " + cacheMissTimeIntervalInMillis + "s,\n" +
                "\tmeasurePerformance = " + measurePerformance + "\n" +
                '}';
    }
}
