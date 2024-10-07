package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOPolicy extends CachingPolicy {
    private final Queue<Page> fifoQ = new LinkedList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.fifoQ.add(page);
        this.showManagementStructures();

        Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page(" + page.getPageName() + ')');
    }

    // FIFO does not have any Post-Page-Access-Action after a page-hit
    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        // Do Nothing
    }

    @Override
    public void evictPage() {
        Page evictablePage = this.fifoQ.poll();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("FIFO Queue cannot be Empty, when trying to evict a page.");
        }

        Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page(" + evictablePage.getPageName() + ')');

        Cache.getInstance().evict(evictablePage.getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.fifoQ, "FIFO Queue:\n");
    }

    @Override
    public String toString() {
        return "First-in First-out (FIFO)";
    }
}
