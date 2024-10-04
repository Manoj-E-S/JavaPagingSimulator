package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

import java.util.LinkedHashSet;
import java.util.Set;

public class AdaptiveReplacementCachePolicy extends CachingPolicy {
    private final Set<Page> recencySet = new LinkedHashSet<>();
    private final Set<Page> frequencySet = new LinkedHashSet<>();
    private final Set<Page> recencyEvictedSet = new LinkedHashSet<>();
    private final Set<Page> frequencyEvictedSet = new LinkedHashSet<>();
    private final int cacheSize = Cache.getGlobalConfig().getFramesInCache();
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
        }

        System.out.println("Recency Balanced Parameter:\n" + this.recencyBasedBalanceParameter);
        System.out.println("Recency Set:\n" + this.recencySet);
        System.out.println("Frequency Set:\n" + this.frequencySet);
        System.out.println("RecencyEvicted Set:\n" + this.recencyEvictedSet);
        System.out.println("FrequencyEvicted Set:\n" + this.frequencyEvictedSet);
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
        System.out.println("Recency Balanced Parameter:\n" + this.recencyBasedBalanceParameter);
        System.out.println("Recency Set:\n" + this.recencySet);
        System.out.println("Frequency Set:\n" + this.frequencySet);
        System.out.println("RecencyEvicted Set:\n" + this.recencyEvictedSet);
        System.out.println("FrequencyEvicted Set:\n" + this.frequencyEvictedSet);
    }

    @Override
    public void evictPage() {
        Page evictedPage;
        if (!recencySet.isEmpty() && (recencySet.size() > recencyBasedBalanceParameter || (recencySet.size() == recencyBasedBalanceParameter && frequencyEvictedSet.size() > recencyEvictedSet.size()))) {
            // Evict from recencySet and move to recencyEvictedSet
            evictedPage = recencySet.iterator().next();
            recencySet.remove(evictedPage);
            recencyEvictedSet.add(evictedPage);
        }
        else {
            // Evict from frequencySet and move to frequencyEvictedSet
            evictedPage = frequencySet.iterator().next();
            frequencySet.remove(evictedPage);
            frequencyEvictedSet.add(evictedPage);
        }
        Cache.getInstance().evict(evictedPage.getPageName());
    }


    private int increaseRecencyBasedBalanceParameter() {
        int possibleRecencyBasedBalanceParameter = this.recencyBasedBalanceParameter + Math.max(1, this.frequencyEvictedSet.size()/this.recencyEvictedSet.size());
        return Math.min(this.cacheSize, possibleRecencyBasedBalanceParameter);
    }


    private int decreaseRecencyBasedBalanceParameter() {
        int possibleRecencyBasedBalanceParameter = this.recencyBasedBalanceParameter - Math.max(1, this.frequencyEvictedSet.size()/this.recencyEvictedSet.size());
        return Math.max(0, possibleRecencyBasedBalanceParameter);
    }

}
