package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeastFrequentlyUsedPolicy extends CachingPolicy {
    private final List<AbstractMap.SimpleEntry<Page, Integer>> lfuList = new ArrayList<AbstractMap.SimpleEntry<Page, Integer>>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.lfuList.add(new AbstractMap.SimpleEntry<Page, Integer>(page, 1));
        System.out.println("MFU-List after Page Access:\n" + this.lfuList);
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        for(var page_freq: this.lfuList) {
            if(page.equals(page_freq.getKey())) {
                page_freq.setValue(page_freq.getValue() + 1);
                break;
            }
        }
        System.out.println("MFU-List after Page Access:\n" + this.lfuList);
    }

    @Override
    public void evictPage() {
        lfuList.sort(Comparator.comparing(AbstractMap.SimpleEntry::getValue));
        System.out.println("Sorted MFU-List before Page Eviction:\n" + this.lfuList);

        // Last Element will be the Most Recently Used and it will be removed
        AbstractMap.SimpleEntry<Page, Integer> evictablePage_freq = lfuList.getFirst();
        if(evictablePage_freq.getKey() == null) {
            // Page is null: Should Never Occur if the app logic is correct
            throw new IllegalStateException("No Page to Evict.");
        }
        this.lfuList.remove(evictablePage_freq);
        Cache.getInstance().evict(evictablePage_freq.getKey().getPageName());
    }
}
