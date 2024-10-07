package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.ArrayList;
import java.util.List;

public class MRUPolicy extends CachingPolicy {
    private final List<Page> mruList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.mruList.add(page);
        this.showManagementStructures();

        Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page");
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        this.mruList.remove(page);
        this.mruList.add(page);
        this.showManagementStructures();
    }

    @Override
    public void evictPage() {
        Page evictablePage = this.mruList.getLast();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("MRU List cannot be Empty, when trying to evict a page.");
        }

        Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page");

        this.mruList.remove(evictablePage);
        Cache.getInstance().evict(evictablePage.getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.mruList, "MRU List:\n");
    }

    @Override
    public String toString() {
        return "Most Recently Used (MRU)";
    }
}
