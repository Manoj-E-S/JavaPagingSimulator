package io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy;

import io.github.manoj_e_s.java_page_simulator.backend_components.Cache;
import io.github.manoj_e_s.java_page_simulator.backend_components.Page;

import java.util.LinkedList;
import java.util.Queue;

public class TwoQPolicy extends CachingPolicy {
    private final Queue<Page> twoQQ = new LinkedList<>();
    private final Queue<Page> recencyQ = new LinkedList<>();

    @Override
    public void policyActionsPostPageAccessOnMiss(Page page) {
        this.twoQQ.add(page);
        System.out.println("Normal Queue:\n" + this.twoQQ);
        System.out.println("Recency Queue:\n" + this.recencyQ);
    }

    @Override
    public void policyActionsPostPageAccessOnHit(Page page) {
        if(!(this.recencyQ.contains(page))) {
            this.twoQQ.remove(page);
            this.recencyQ.add(page);
        } else {
            this.recencyQ.remove(page);
            this.recencyQ.add(page);
        }
        System.out.println("Normal Queue:\n" + this.twoQQ);
        System.out.println("Recency Queue:\n" + this.recencyQ);
    }

    @Override
    public void evictPage() {
        if(this.twoQQ.isEmpty()) {
            while (this.recencyQ.peek() != null) {
                this.twoQQ.add(this.recencyQ.poll());
            }
        }

        Page evictablePage = this.twoQQ.poll();
        if(evictablePage == null) {
            // Should Never Occur if the app logic is correct
            throw new IllegalStateException("Normal Queue cannot be Empty, when trying to evict a page.");
        }
        Cache.getInstance().evict(evictablePage.getPageName());
    }
}
