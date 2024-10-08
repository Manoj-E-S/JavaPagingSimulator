package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class LeastFrequentlyUsedPolicy extends CachingPolicy {
    private final List<AbstractMap.SimpleEntry<Page, Integer>> lfuList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.lfuList.add(new AbstractMap.SimpleEntry<>(page, 1));
        this.showManagementStructures();

        Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize() + Integer.BYTES);
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page(" + page.getPageName() + ')');
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        for(var page_freq: this.lfuList) {
            if(page.equals(page_freq.getKey())) {
                page_freq.setValue(page_freq.getValue() + 1);
                break;
            }
        }
        this.showManagementStructures();
    }

    @Override
    public void evictPage() {
        lfuList.sort(Entry.comparingByValue());
        this.showSortedMFUList();

        // Last Element will be the Most Recently Used and it will be removed
        AbstractMap.SimpleEntry<Page, Integer> evictablePage_freq = lfuList.getFirst();
        if(evictablePage_freq.getKey() == null) {
            // Page is null: Should Never Occur if the app logic is correct
            throw new IllegalStateException("No Page to Evict.");
        }

        Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage_freq.getKey().getByteSize() + Integer.BYTES);
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page(" + evictablePage_freq.getKey().getPageName() + ')');

        this.lfuList.remove(evictablePage_freq);
        Cache.getInstance().evict(evictablePage_freq.getKey().getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.lfuList, "LFU-List after Page Access:\n");
    }

    @Override
    public String toString() {
        return "Least Frequently used (LFU)";
    }

    private void showSortedMFUList() {
        Logger.getInstance().logVerbose(this.lfuList, "Sorted LFU-List before Page Eviction:\n");
    }
}
