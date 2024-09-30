package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.util.Objects;

public class Page {

    // Page Name
    public final String pageName;

    // Page Number
    private static int pageNumberCounter = 0;
    public final int pageNumber;


    // Getters and Setters
    public String getPageName() {
        return pageName;
    }

    public int getPageNumber() {
        return pageNumber;
    }


    // All Params Constructor
    private Page(String pageName) {
        this.pageName = pageName;

        this.pageNumber = Page.pageNumberCounter;
        Page.pageNumberCounter++;
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
    public void run(int timeToExecuteInSeconds) {
        System.out.println("Executing: Page(" + this.pageName + ')');
        DelayHandler.delayBySeconds(timeToExecuteInSeconds, "Execution (" + timeToExecuteInSeconds + "s)\n");
    }
}
