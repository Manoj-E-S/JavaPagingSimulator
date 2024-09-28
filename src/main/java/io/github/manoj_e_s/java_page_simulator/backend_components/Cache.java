package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.util.HashMap;
import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.CachingPolicy;

public class Cache {

    // Cache Size
    public final int numOfFrames;

    // Caching Policy
    public CachingPolicy cachingPolicy;

    // Frames K: pageName, V: Page
    private final HashMap<String, Page> frames;


    // GlobalConfig Param Constructor
    public Cache(GlobalConfig gc) {
        this.numOfFrames = gc.getFramesInCache();
        this.cachingPolicy = gc.getCachingPolicy();
        this.frames = new HashMap<String, Page>();
    }

    // Put data
    public HashMap<String, Page> put(Page page) {
        if(frames.size() == this.numOfFrames) {
            // TODO: CachePolicy implementation
            System.out.println("Cache full. CachePolicy Yet to be implemented :(");
            return null;
        }
        this.frames.put(page.getPageName(), page);
        return this.frames;
    }

    // Get page
    public Page get(String pageName) {
        return this.frames.get(pageName);
    }

}
