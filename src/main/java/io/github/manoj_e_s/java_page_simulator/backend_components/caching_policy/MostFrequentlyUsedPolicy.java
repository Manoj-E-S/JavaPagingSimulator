package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class MostFrequentlyUsedPolicy extends CachingPolicy {
    private final List<AbstractMap.SimpleEntry<Page, Integer>> mfuList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.mfuList.add(new AbstractMap.SimpleEntry<>(page, 1));
        System.out.println("MFU-List after Page Access:\n" + this.mfuList);
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        for(var page_freq: this.mfuList) {
            if(page.equals(page_freq.getKey())) {
                page_freq.setValue(page_freq.getValue() + 1);
                break;
            }
        }
        System.out.println("MFU-List after Page Access:\n" + this.mfuList);
    }

    @Override
    public void evictPage() {
        mfuList.sort((a, b) -> -(a.getValue().compareTo(b.getValue())));
        System.out.println("Sorted MFU-List before Page Eviction:\n" + this.mfuList);

        // Last Element will be the Most Recently Used and it will be removed
        AbstractMap.SimpleEntry<Page, Integer> evictablePage_freq = mfuList.getFirst();
        if(evictablePage_freq.getKey() == null) {
            // Page is null: Should Never Occur if the app logic is correct
            throw new IllegalStateException("No Page to Evict.");
        }
        this.mfuList.remove(evictablePage_freq);
        Cache.getInstance().evict(evictablePage_freq.getKey().getPageName());
    }
}
