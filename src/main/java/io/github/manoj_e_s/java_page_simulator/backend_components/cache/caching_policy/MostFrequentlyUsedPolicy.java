package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class MostFrequentlyUsedPolicy extends CachingPolicy {
    private final List<AbstractMap.SimpleEntry<Page, Integer>> mfuList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.mfuList.add(new AbstractMap.SimpleEntry<>(page, 1));
        this.showManagementStructures();

        Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize() + Integer.BYTES);
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page");
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        for(var page_freq: this.mfuList) {
            if(page.equals(page_freq.getKey())) {
                page_freq.setValue(page_freq.getValue() + 1);
                break;
            }
        }
        this.showManagementStructures();
    }

    @Override
    public void evictPage() {
        mfuList.sort((a, b) -> -(a.getValue().compareTo(b.getValue())));
        this.showSortedMFUList();

        // Last Element will be the Most Recently Used and it will be removed
        AbstractMap.SimpleEntry<Page, Integer> evictablePage_freq = mfuList.getFirst();
        if(evictablePage_freq.getKey() == null) {
            // Page is null: Should Never Occur if the app logic is correct
            throw new IllegalStateException("No Page to Evict.");
        }

        Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage_freq.getKey().getByteSize() + Integer.BYTES);
        Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page");

        this.mfuList.remove(evictablePage_freq);
        Cache.getInstance().evict(evictablePage_freq.getKey().getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.mfuList, "MFU-List after Page Access:\n");
    }

    @Override
    public String toString() {
        return "Most Frequently Used (MFU)";
    }

    private void showSortedMFUList() {
        Logger.getInstance().logVerbose(this.mfuList, "Sorted MFU-List before Page Eviction:\n");
    }
}
