package io.github.manoj_e_s.java_page_simulator.backend_components.page;

import io.github.manoj_e_s.java_page_simulator.backend_components.disk.Disk;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.DelayHandler;
import io.github.manoj_e_s.java_page_simulator.backend_components.performance.Logger;

import java.util.Objects;

public class Page {

    // Page Name
    public final String pageName;

    // Page Number
    private static int pageNumberCounter = 0;
    public final int pageNumber;

    // Size of Object in BYTES
    public int byteSize;


    // Getters and Setters
    public String getPageName() {
        return pageName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getByteSize() {
        return byteSize;
    }

    // All Params Constructor
    private Page(String pageName) {
        this.pageName = pageName;

        this.pageNumber = Page.pageNumberCounter;
        Page.pageNumberCounter++;

        this.byteSize = (2 * Integer.BYTES) + (Character.BYTES * pageName.length());
    }

    // Page Factory
    public static synchronized void createPage(String pageName) {
        Disk disk = Disk.getInstance();
        for(Page page: disk.values()) {
            if(page.getPageName().equals(pageName)) {
                return;
            }
        }

        Page newPage = new Page(pageName);
        disk.put(pageName, newPage);
    }


    // METHODS

    @Override
    public String toString() {
        return "Page {\n" +
                "\tpageName = '" + pageName + "',\n" +
                "\tpageNumber = " + pageNumber + "\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(pageName, page.pageName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageName);
    }

    // run
    public void run(int timeToExecuteInMillis) {
        Logger.getInstance().log(null, "Executing: Page(" + this.pageName + ')');
        DelayHandler.delayByMillis(timeToExecuteInMillis, "Execution (" + timeToExecuteInMillis + "ms)\n");
        Logger.getInstance().log(null, "------\n");
    }
}
