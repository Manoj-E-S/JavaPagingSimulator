package io.github.manoj_e_s.java_page_simulator.backend_components.disk;

import io.github.manoj_e_s.java_page_simulator.backend_components.page.Page;

import java.util.HashMap;

// Singleton Hashmap -> K: pageName, V: Page
public class Disk extends HashMap<String, Page> {
    private static Disk instance = null;

    // PRIVATE CONSTRUCTOR
    private Disk() {
        super();
    }

    // METHODS
    public static synchronized Disk getInstance() {
        if (Disk.instance == null)
            Disk.instance = new Disk();

        return instance;
    }

    @Override
    public String toString() {
        return "Disk " + super.toString();
    }
}
