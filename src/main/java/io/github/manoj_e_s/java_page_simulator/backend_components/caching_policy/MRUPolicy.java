package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

import java.util.ArrayList;
import java.util.List;

public class MRUPolicy extends CachingPolicy {
    private final List<Page> mruList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.mruList.add(page);
        System.out.println(this.mruList);
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        this.mruList.remove(page);
        this.mruList.add(page);
        System.out.println(this.mruList);
    }

    @Override
    public void evictPage() {
        Page evictablePage = this.mruList.getLast();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("MRU List cannot be Empty, when trying to evict a page.");
        }
        this.mruList.remove(evictablePage);
        Cache.getInstance().evict(evictablePage.getPageName());
    }
}
