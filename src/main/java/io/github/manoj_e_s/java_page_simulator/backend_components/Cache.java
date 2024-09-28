package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.util.HashMap;
import io.github.manoj_e_s.java_page_simulator.backend_components.caching_policy.CachingPolicy;

// Singleton Cache
public class Cache {

    // Cache Size
    public final int numOfFrames;

    // Caching Policy
    public CachingPolicy cachingPolicy;

    // Frames K: pageName, V: Page
    private final HashMap<String, Page> frames;


    // INSTANCE
    private static Cache instance = null;


    // GlobalConfig PARAM CONSTRUCTOR
    private Cache(GlobalConfig gc) {
        this.numOfFrames = gc.getFramesInCache();
        this.cachingPolicy = gc.getCachingPolicy();
        this.frames = new HashMap<String, Page>();
    }


    // INSTANCE GETTER
    public static synchronized Cache getInstance(GlobalConfig gc) {
        if (instance == null)
            instance = new Cache(gc);

        return instance;
    }


    // METHODS

    @Override
    public String toString() {
        return "Cache {\n" +
                "\tnumOfFrames = " + numOfFrames + ",\n" +
                "\tcachingPolicy = " + cachingPolicy + ",\n" +
                "\tframes = " + frames + ",\n" +
                '}';
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
