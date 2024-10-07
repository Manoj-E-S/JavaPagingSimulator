package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class TwoQPolicy extends CachingPolicy {
    private final Queue<Page> recencyQ = new LinkedList<>();
    private final Queue<Page> frequencyQ = new LinkedList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.recencyQ.add(page);
        this.showManagementStructures();

        Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page(" + page.getPageName() + ')');
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        if(!(this.frequencyQ.contains(page))) {
            this.recencyQ.remove(page);
            this.frequencyQ.add(page);
        } else {
            this.frequencyQ.remove(page);
            this.frequencyQ.add(page);
        }
        this.showManagementStructures();
    }

    @Override
    public void evictPage() {
        if(this.recencyQ.isEmpty()) {
            this.recencyQ.add(this.frequencyQ.poll());
        }

        Page evictablePage = this.recencyQ.poll();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("Recency Queue cannot be Empty, when trying to evict a page.");
        }

        Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page(" + evictablePage.getPageName() + ')');

        Cache.getInstance().evict(evictablePage.getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.recencyQ, "Recency Queue:\n");
        Logger.getInstance().logVerbose(this.frequencyQ, "Frequency Queue:\n");
    }

    @Override
    public String toString() {
        return "Two Queue (2Q)";
    }
}
