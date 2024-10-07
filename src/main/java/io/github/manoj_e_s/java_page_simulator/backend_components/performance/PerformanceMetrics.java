package io.github.manoj_e_s.java_page_simulator.backend_components.performance;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerformanceMetrics {
    private long hits;
    private long misses;
    private long evictions;
    private long accessStartTime; // in ns
    private long accessEndTime; // in ns
    private long usedMemory; // in KB
    private final List<Long> accessTimes = new ArrayList<>();
    private final HashMap<String, Long> memoryUsages = new HashMap<>();

    public void startTimer() {
        this.accessStartTime = System.nanoTime();
    }

    public void stopTimer() {
        this.accessEndTime = System.nanoTime();
        long accessTimeDelta = this.getAccessTime();
        this.accessTimes.add(accessTimeDelta);
        Logger.getInstance().logVerbose(null, String.format("Access Time: %.2f ms", (double) accessTimeDelta / 1000000));
    }

    public void incrementMemoryByPageSize() {
        this.usedMemory += Cache.getCacheConfig().getPageSizeInKb();
    }

    public void decrementMemoryByPageSize() {
        this.usedMemory -= Cache.getCacheConfig().getPageSizeInKb();
    }

    public void incrementMemoryInBytes(long bytes) {
        this.usedMemory += (bytes / 1024);
    }

    public void decrementMemoryInBytes(long bytes) {
        this.usedMemory -= (bytes / 1024);
    }

    public void recordHit() {
        this.hits++;
    }

    public void recordMiss() {
        this.misses++;
    }

    public void recordEviction() {
        this.evictions++;
    }

    public void recordMemoryUsage(String operation) {
        this.memoryUsages.put(operation, this.usedMemory);
        Logger.getInstance().logVerbose(null, "Memory used after " + operation + ": " + usedMemory + " KB");
    }

    public double getHitRatio() {
        return (double) this.hits / (this.hits + this.misses);
    }

    public double getMissRatio() {
        return (double) this.misses / (this.hits + this.misses);
    }

    public double getEvictionRatio() {
        return (double) this.evictions / (this.hits + this.misses);
    }

    public double getAvgAccessTime() {
        double sum = 0;
        for (long accessTime: this.accessTimes) {
            sum += (double) accessTime / 1000000; // in ms
        }
        return sum / this.accessTimes.size();
    }

    public double getAvgMemoryUsage() {
        double sum = 0;
        for (long memoryUsage: this.memoryUsages.values()) {
            sum += (double) memoryUsage; // in KB
        }
        return sum / this.memoryUsages.size();
    }

    public void log() {
        Logger.getInstance().log(null, String.format("Hit Ratio: %.2f", this.getHitRatio()));
        Logger.getInstance().log(null, String.format("Miss Ratio:  %.2f", this.getMissRatio()));
        Logger.getInstance().log(null, String.format("Eviction Ratio:  %.2f", this.getEvictionRatio()));
        Logger.getInstance().log(null, String.format("Average access time:  %.2f ms", this.getAvgAccessTime()));
        Logger.getInstance().log(null, String.format("Average Memory Usage:  %.2f KB", this.getAvgMemoryUsage()));
    }

    private long getAccessTime() {
        return this.accessEndTime - this.accessStartTime;
    }
}
