package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;


import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.DelayHandler;
import io.github.manoj_e_s.java_page_simulator.backend_components.Disk;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

public abstract class CachingPolicy {

    protected void delayByHit() {
        int delay = Cache.getGlobalConfig().getCacheHitTimeIntervalInSeconds();
        DelayHandler.delayBySeconds(delay, "Cache Hit (" + delay + "s)");
    }

    protected void delayByMiss() {
        int delay = Cache.getGlobalConfig().getCacheMissTimeIntervalInSeconds();
        DelayHandler.delayBySeconds(delay, "Cache Miss (" + delay + "s)");
    }

    // Handle Cache Hit
    public void handleHit(Page page) {
        this.delayByHit();

        // Post Page Access Action
        this.policyActionsPostPageAccessOnHit(page);
    }

    // Handle Cache Miss
    public Page handleMiss(String pageName) {
        this.delayByMiss();

        if(Cache.getInstance().isFull()) {
            // Page Fault when Cache is full
            this.evictPage();
        }

        // Page Fault when Cache is yet not full
        Page requiredPage = Disk.getInstance().get(pageName);
        Cache.getInstance().put(requiredPage);

        // Post Page Access Action
        this.policyActionsPostPageAccessOnMiss(requiredPage);
        return requiredPage;
    }

    // Page Access - Every Request for a Page from the cache (ie. Cache::get() call is made)
    // A Cache::get() can result in a page-hit or a page-miss.
    // Called just before CachingPolicy::handleHit() and CachingPolicy::handleMiss() return.
    public abstract void policyActionsPostPageAccessOnMiss(Page page);
    public abstract void policyActionsPostPageAccessOnHit(Page page);

    // Evict Page - Page Eviction on Page-miss, and the Cache is Full
    public abstract void evictPage();

}
