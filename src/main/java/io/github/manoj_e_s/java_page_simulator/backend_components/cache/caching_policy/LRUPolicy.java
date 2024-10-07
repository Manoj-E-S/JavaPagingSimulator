package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.ArrayList;
import java.util.List;

public class LRUPolicy extends CachingPolicy {
    private final List<Page> lruList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.lruList.add(page);
        this.showManagementStructures();

        Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page(" + page.getPageName() + ')');
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        this.lruList.remove(page);
        this.lruList.add(page);
        this.showManagementStructures();
    }

    @Override
    public void evictPage() {
        Page evictablePage = this.lruList.getFirst();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("LRU List cannot be Empty, when trying to evict a page.");
        }

        Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage.getByteSize());
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page(" + evictablePage.getPageName() + ')');

        this.lruList.remove(evictablePage);
        Cache.getInstance().evict(evictablePage.getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.lruList, "LRU List:\n");
    }

    @Override
    public String toString() {
        return "Least Recently Used (LRU)";
    }
}
