package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.util.Objects;

public class Page {

    // Page Name
    public final String pageName;

    // Page Number
    private static int pageNumberCounter = 0;
    public final int pageNumber;

    // time to execute
    public final int timeToExecuteInSeconds;


    // Getters and Setters
    public String getPageName() {
        return pageName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTimeToExecuteInSeconds() {
        return timeToExecuteInSeconds;
    }


    // All Params Constructor
    private Page(String pageName, int timeToExecuteInSeconds) {
        this.pageName = pageName;
        this.timeToExecuteInSeconds = timeToExecuteInSeconds;

        this.pageNumber = Page.pageNumberCounter;
        Page.pageNumberCounter++;
    }

    // Page Factory
    public static Page createPage(String pageName, int timeToExecuteInSeconds) {
        Disk disk = Disk.getInstance();
        for(Page page: disk.values()) {
            if(page.getPageName().equals(pageName)) {
                return page;
            }
        }

        Page newPage = new Page(pageName, timeToExecuteInSeconds);
        disk.put(pageName, newPage);
        return newPage;
    }


    // METHODS

    @Override
    public String toString() {
        return "Page {\n" +
                "\tpageName = '" + pageName + "',\n" +
                "\tpageNumber = " + pageNumber + ",\n" +
                "\ttimeToExecuteInSeconds = " + timeToExecuteInSeconds + "\n" +
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
    public void run() {
        try {
            System.out.println("Executing: Page(" + this.pageName + ')');
            Thread.sleep(this.timeToExecuteInSeconds * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
