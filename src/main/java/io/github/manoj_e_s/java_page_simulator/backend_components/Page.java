package io.github.manoj_e_s.java_page_simulator.backend_components;

public class Page {

    // Page Name
    public final String pageName;

    // Parent Process
    public final int parentPid;

    // Page Number
    private static int pageNumberCounter = 0;
    public final int pageNumber;

    // time to execute
    public final int timeToExecuteInSeconds;


    // Getters and Setters
    public String getPageName() {
        return pageName;
    }

    public int getParentPid() {
        return parentPid;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTimeToExecuteInSeconds() {
        return timeToExecuteInSeconds;
    }


    // All Params Constructor
    public Page(String pageName, int parentPid, int timeToExecuteInSeconds) {
        this.pageName = pageName;
        this.parentPid = parentPid;
        this.timeToExecuteInSeconds = timeToExecuteInSeconds;
        this.pageNumber = Page.pageNumberCounter;
        Page.pageNumberCounter++;
    }
}
