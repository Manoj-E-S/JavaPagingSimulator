package io.github.manoj_e_s.java_page_simulator.backend_components.cache.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.cache.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.LinkedHashSet;
import java.util.Set;

public class AdaptiveReplacementCachePolicy extends CachingPolicy {
    private final Set<Page> recencySet = new LinkedHashSet<>();
    private final Set<Page> frequencySet = new LinkedHashSet<>();
    private final Set<Page> recencyEvictedSet = new LinkedHashSet<>();
    private final Set<Page> frequencyEvictedSet = new LinkedHashSet<>();
    private int recencyBasedBalanceParameter = 0;

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        if (this.recencyEvictedSet.contains(page)) {
            this.recencyBasedBalanceParameter = this.increaseRecencyBasedBalanceParameter();
            this.recencyEvictedSet.remove(page);
            this.frequencySet.add(page);
        }
        else if (this.frequencyEvictedSet.contains(page)) {
            this.recencyBasedBalanceParameter = this.decreaseRecencyBasedBalanceParameter();
            this.frequencyEvictedSet.remove(page);
            this.frequencySet.add(page);
        }
        else {
            this.recencySet.add(page);
            Cache.getPerformanceMetrics().incrementMemoryInBytes(page.getByteSize());
            Cache.getPerformanceMetrics().recordMemoryUsage("Policy Insert Page(" + page.getPageName() + ')');
        }
        this.showManagementStructures();
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        if(this.recencySet.contains(page)) {
            this.recencySet.remove(page);
            this.frequencySet.add(page);
        } else {
            this.frequencySet.remove(page);
            this.frequencySet.add(page);
        }
        this.showManagementStructures();
    }

    @Override
    public void evictPage() {
        Page evictedPage;
        if (!recencySet.isEmpty() && (recencySet.size() > recencyBasedBalanceParameter || (recencySet.size() == recencyBasedBalanceParameter && frequencyEvictedSet.size() > recencyEvictedSet.size()))) {
            // Prioritize Frequent Pages, and clear from History if required
            this.removeHistoryPrioritizingFrequency();

            // Evict from recencySet and move to recencyEvictedSet
            evictedPage = recencySet.iterator().next();
            if(evictedPage == null) {
                // Should Never Occur if the app logic is correct
                throw new IllegalStateException("Recency Set cannot be Empty, when trying to evict a page.");
            }
            recencySet.remove(evictedPage);
            recencyEvictedSet.add(evictedPage);
        }
        else {
            // Prioritize Recent Pages, and clear from History if required
            this.removeHistoryPrioritizingRecency();

            // Evict from frequencySet and move to frequencyEvictedSet
            evictedPage = frequencySet.iterator().next();
            if(evictedPage == null) {
                // Should Never Occur if the app logic is correct
                throw new IllegalStateException("Frequency Set cannot be Empty, when trying to evict a page.");
            }
            frequencySet.remove(evictedPage);
            frequencyEvictedSet.add(evictedPage);
        }
        Cache.getInstance().evict(evictedPage.getPageName());
    }

    @Override
    protected void showManagementStructures() {
        Logger.getInstance().logVerbose(this.recencyBasedBalanceParameter, "Recency Balanced Parameter:\n");
        Logger.getInstance().logVerbose(this.recencySet, "Recency Set:\n");
        Logger.getInstance().logVerbose(this.frequencySet, "Frequency Set:\n");
        Logger.getInstance().logVerbose(this.recencyEvictedSet, "RecencyEvicted Set:\n");
        Logger.getInstance().logVerbose(this.frequencyEvictedSet, "FrequencyEvicted Set:\n");
    }

    @Override
    public String toString() {
        return "Adaptive Replacement Cache (ARC)";
    }

    private int increaseRecencyBasedBalanceParameter() {
        int possibleRecencyBasedBalanceParameter = this.recencyBasedBalanceParameter + Math.max(1, this.frequencyEvictedSet.size()/this.recencyEvictedSet.size());
        return Math.min(Cache.getCacheConfig().getFramesInCache(), possibleRecencyBasedBalanceParameter);
    }


    private int decreaseRecencyBasedBalanceParameter() {
        if (this.recencyEvictedSet.isEmpty()) {
            return 0;
        }
        int possibleRecencyBasedBalanceParameter = this.recencyBasedBalanceParameter - Math.max(1, this.frequencyEvictedSet.size()/this.recencyEvictedSet.size());
        return Math.max(0, possibleRecencyBasedBalanceParameter);
    }

    private void removeHistoryPrioritizingRecency() {
        if (this.recencyEvictedSet.size() + this.frequencyEvictedSet.size() == 2 * Cache.getCacheConfig().getFramesInCache()) {
            // History Size cannot exceed (2 * CacheSize)
            Page evictablePage;
            if (!this.frequencyEvictedSet.isEmpty()) {
                evictablePage = this.frequencyEvictedSet.iterator().next();
                this.frequencyEvictedSet.remove(evictablePage);
            } else {
                evictablePage = this.recencyEvictedSet.iterator().next();
                this.recencyEvictedSet.remove(evictablePage);
            }
            Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage.getByteSize());
            Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page(" + evictablePage.getPageName() + ')');
        }
    }

    private void removeHistoryPrioritizingFrequency() {
        if (this.recencyEvictedSet.size() + this.frequencyEvictedSet.size() == 2 * Cache.getCacheConfig().getFramesInCache()) {
            // History Size cannot exceed (2 * CacheSize)
            Page evictablePage;
            if (!this.recencyEvictedSet.isEmpty()) {
                evictablePage = this.recencyEvictedSet.iterator().next();
                this.recencyEvictedSet.remove(evictablePage);
            } else {
                evictablePage = this.frequencyEvictedSet.iterator().next();
                this.frequencyEvictedSet.remove(evictablePage);
            }
            Cache.getPerformanceMetrics().decrementMemoryInBytes(evictablePage.getByteSize());
            Cache.getPerformanceMetrics().recordMemoryUsage("Policy Evict Page(" + evictablePage.getPageName() + ')');
        }
    }

}
