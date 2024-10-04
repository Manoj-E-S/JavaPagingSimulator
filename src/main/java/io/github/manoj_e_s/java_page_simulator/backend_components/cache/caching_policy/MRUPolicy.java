package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;

import java.util.ArrayList;
import java.util.List;

public class MRUPolicy extends CachingPolicy {
    private final List<Page> mruList = new ArrayList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.mruList.add(page);
        this.showManagementStructures();
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
        this.mruList.remove(evictablePage);
        Cache.getInstance().evict(evictablePage.getPageName());
    }

    @Override
    protected void showManagementStructures() {
        System.out.println("MRU List:\n" + this.mruList);
    }
}
