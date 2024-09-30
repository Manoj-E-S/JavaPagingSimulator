package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

import java.util.ArrayList;
import java.util.List;

public class LRUPolicy extends CachingPolicy {
    private final List<Page> lruList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.lruList.add(page);
        System.out.println(this.lruList);
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        this.lruList.remove(page);
        this.lruList.add(page);
        System.out.println(this.lruList);
    }

    @Override
    public void evictPage() {
        Page evictablePage = this.lruList.getFirst();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("LRU List cannot be Empty, when trying to evict a page.");
        }
        this.lruList.remove(evictablePage);
        Cache.getInstance().evict(evictablePage.getPageName());
    }
}
