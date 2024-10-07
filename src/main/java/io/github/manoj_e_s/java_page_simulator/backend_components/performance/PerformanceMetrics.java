package io.github.manoj_e_s.java_page_simulator.backend_components.performance;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PerformanceMetrics {
    private long perProcessHits;
    private long perProcessMisses;
    private long perProcessEvictions;
    private long netHits;
    private long netMisses;
    private long netEvictions;
    private long accessStartTime; // in ns
    private long accessEndTime; // in ns
    private long usedMemory; // in Bytes
    private List<Long> accessTimes = new ArrayList<>();
    private final LinkedHashMap<String, Long> memoryUsages = new LinkedHashMap<>();

    public void startTimer() {
        this.accessStartTime = System.nanoTime();
    }

    public void stopTimer() {
        this.accessEndTime = System.nanoTime();
        long accessTimeDelta = this.getAccessTimeDelta();
        this.accessTimes.add(accessTimeDelta);
        Logger.getInstance().logVerbose(null, String.format("Access Time: %.2f ms", (double) accessTimeDelta / 1000000));
    }

    public void incrementMemoryByPageSize() {
        this.usedMemory += Cache.getCacheConfig().getPageSizeInBytes();
    }

    public void decrementMemoryByPageSize() {
        this.usedMemory -= Cache.getCacheConfig().getPageSizeInBytes();
    }

    public void incrementMemoryInBytes(long bytes) {
        this.usedMemory += bytes;
    }

    public void decrementMemoryInBytes(long bytes) {
        this.usedMemory -= bytes;
    }

    public void recordHit() {
        this.perProcessHits++;
    }

    public void recordMiss() {
        this.perProcessMisses++;
    }

    public void recordEviction() {
        this.perProcessEvictions++;
    }

    public void recordMemoryUsage(String operation) {
        this.memoryUsages.put(operation, this.usedMemory);
        Logger.getInstance().logVerbose(null, "Memory used after " + operation + ": " + usedMemory / 1024 + " KB");
    }

    public LinkedHashMap<String, Long> getMemoryUsages() {
        return memoryUsages;
    }

    public List<Long> getAccessTimes() {
        return accessTimes;
    }

    public List<Long> clearAccessTimes() {
        List<Long> prevAccessTimes = this.accessTimes;
        this.accessTimes = new ArrayList<>();
        return prevAccessTimes;
    }

    public void clearHits() {
        this.netHits += this.perProcessHits;
        this.perProcessHits = 0;
    }

    public void clearMisses() {
        this.netMisses += this.perProcessMisses;
        this.perProcessMisses = 0;
    }

    public void clearEvictions() {
        this.netEvictions += this.perProcessEvictions;
        this.perProcessEvictions = 0;
    }

    public double getPerProcessHitRatio() {
        return (double) this.perProcessHits / (this.perProcessHits + this.perProcessMisses);
    }

    public double getPerProcessMissRatio() {
        return (double) this.perProcessMisses / (this.perProcessHits + this.perProcessMisses);
    }

    public double getPerProcessEvictionRatio() {
        return (double) this.perProcessEvictions / (this.perProcessHits + this.perProcessMisses);
    }

    public double getNetHitRatio() {
        return (double) this.netHits / (this.netHits + this.netMisses);
    }

    public double getNetMissRatio() {
        return (double) this.netMisses / (this.netHits + this.netMisses);
    }

    public double getNetEvictionRatio() {
        return (double) this.netEvictions / (this.netHits + this.netMisses);
    }

    public double getPerProcessAvgAccessTime() {
        double sum = 0;
        for (long accessTime: this.accessTimes) {
            sum += (double) accessTime / 1000000; // in ms
        }
        return sum / this.accessTimes.size();
    }

    public double getPerProcessAvgAccessTime(List<Long> prevAccessTimes) {
        double sum = 0;
        for (long accessTime: prevAccessTimes) {
            sum += (double) accessTime / 1000000; // in ms
        }
        return sum / prevAccessTimes.size();
    }

    public double getAvgMemoryUsage() {
        double sum = 0;
        for (long memoryUsage: this.memoryUsages.values()) {
            sum += (double) memoryUsage / 1024; // in KB
        }
        // Return Average Per-Process-Memory-Usage if the process uses lesser memory by its termination, otherwise return the memory it used last
        return sum / this.memoryUsages.size();
    }

    // Resets variables and returns previous access time list for possible logging
    public List<Long> resetPerformanceMetrics() {
        this.clearHits();
        this.clearMisses();
        this.clearEvictions();
        return this.clearAccessTimes();
    }

    public void perProcessLog() {
        if (Cache.getCacheConfig().isMeasurePerformance()) {
            Logger.getInstance().log(null, String.format("Hit Ratio: %.2f", this.getPerProcessHitRatio()));
            Logger.getInstance().log(null, String.format("Miss Ratio:  %.2f", this.getPerProcessMissRatio()));
            Logger.getInstance().log(null, String.format("Eviction Ratio:  %.2f", this.getPerProcessEvictionRatio()));
            Logger.getInstance().log(null, String.format("Average access time:  %.2f ms", this.getPerProcessAvgAccessTime()));
            Logger.getInstance().logVerbose(null, String.format("Access Times per page requested by the process: " + this.getAccessTimes().stream().map(item -> (double) item / 1000000).toList()));
        }
    }

    public void netLog() {
        if (Cache.getCacheConfig().isMeasurePerformance()) {
            Logger.getInstance().log(null, String.format("Net Hit Ratio: %.2f", this.getNetHitRatio()));
            Logger.getInstance().log(null, String.format("Net Miss Ratio:  %.2f", this.getNetMissRatio()));
            Logger.getInstance().log(null, String.format("Net Eviction Ratio:  %.2f", this.getNetEvictionRatio()));
            Logger.getInstance().log(null, String.format("Average Memory Usage:  %.10f KB", this.getAvgMemoryUsage()));
            Logger.getInstance().logVerbose(null, String.format("Memory Usage List: " + this.getMemoryUsages()));
        }
    }

    public void netLog(List<Long> prevAccessTimes) {
        if (Cache.getCacheConfig().isMeasurePerformance()) {
            Logger.getInstance().log(null, String.format("Net Hit Ratio: %.2f", this.getNetHitRatio()));
            Logger.getInstance().log(null, String.format("Net Miss Ratio:  %.2f", this.getNetMissRatio()));
            Logger.getInstance().log(null, String.format("Net Eviction Ratio:  %.2f", this.getNetEvictionRatio()));
            Logger.getInstance().log(null, String.format("Average access time:  %.2f ms", this.getPerProcessAvgAccessTime(prevAccessTimes)));
            Logger.getInstance().log(null, String.format("Average Memory Usage:  %.10f KB", this.getAvgMemoryUsage()));
            Logger.getInstance().logVerbose(null, String.format("Memory Usage List: " + this.getMemoryUsages()));
            Logger.getInstance().logVerbose(null, String.format("Access Times per page requested by the process: " + prevAccessTimes.stream().map(item -> (double) item / 1000000).toList()));
        }
    }

    private long getAccessTimeDelta() {
        return this.accessEndTime - this.accessStartTime;
    }
}
