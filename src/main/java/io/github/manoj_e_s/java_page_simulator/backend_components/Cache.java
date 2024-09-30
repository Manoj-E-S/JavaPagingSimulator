package io.github.manoj_e_s.java_page_simulator.backend_components;

import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.CachingPolicy;

import java.util.HashMap;

// Singleton Cache
public class Cache {

    // Caching Policy
    public CachingPolicy cachingPolicy;

    // Frames K: pageName, V: Page
    private final HashMap<String, Page> frames;

    // Global Config
    private static GlobalConfig globalConfig;


    // INSTANCE
    private static Cache instance = null;


    // GETTERS AND SETTERS
    public static GlobalConfig getGlobalConfig() {
        return globalConfig;
    }


    // GlobalConfig PARAM CONSTRUCTOR
    private Cache(GlobalConfig gc) {
        Cache.globalConfig = gc;
        this.cachingPolicy = gc.getCachingPolicy();
        this.frames = new HashMap<>();
    }


    // INSTANCE RELATED METHODS
    // Instantiate
    public static void instantiate(GlobalConfig globalConfig) {
        Cache.instance = new Cache(globalConfig);
    }

    // Get Instance
    public static synchronized Cache getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Cache has not been initialized with GlobalConfig yet.\nUse Cache.instantiate(globalConfig)");
        }
        return instance;
    }

    // Clear the Cache
    public static synchronized void clear() {
        instance = null;
        globalConfig = null;
    }


    // METHODS

    @Override
    public String toString() {
        return "Cache {\n" +
                "\tnumOfFrames = " + globalConfig.getFramesInCache() + ",\n" +
                "\tcachingPolicy = " + cachingPolicy + ",\n" +
                "\tframes = " + frames + "\n" +
                '}';
    }

    // Is Cache Full?
    public boolean isFull() {
        return this.frames.size() >= globalConfig.getFramesInCache();
    }

    // Put page
    public void put(Page page) {
        if(page == null) {
            throw new IllegalStateException("Page cannot be NULL while putting into the Cache");
        }
        this.frames.put(page.getPageName(), page);
    }

    // Get page
    public Page get(String pageName) {
        Page requiredPage = this.frames.get(pageName);
        if(requiredPage != null) {
            // Cache Hit
            cachingPolicy.handleHit(requiredPage);
            return requiredPage;
        }

        // Cache Miss
        // Add required page to cache by Policy and get it
        requiredPage = cachingPolicy.handleMiss(pageName);
        return requiredPage;
    }

    // Evict page
    public void evict(String pageName) {
        this.frames.remove(pageName);
    }

}
