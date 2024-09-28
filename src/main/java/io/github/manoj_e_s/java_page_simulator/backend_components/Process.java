package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Process {

    // process name
    public final String name;

    // pid
    private static int pidCounter = 0;
    public final int pid;

    // process file path
    public final String processFilePath;

    // Its Pages
    private List<String> pageLines = new ArrayList<>();


    // GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public int getPid() {
        return pid;
    }

    public String getProcessFilePath() {
        return processFilePath;
    }


    // ALL PARAMS CONSTRUCTOR
    public Process(String processFilePath, String name) throws IOException {
        this.processFilePath = processFilePath;
        this.name = name;

        this.pid = Process.pidCounter;
        Process.pidCounter++;

        this.pageLines = this.addPagesToDisk();
    }


    // METHODS
    @Override
    public String toString() {
        return "Process {\n" +
                "\tname = '" + name + "',\n" +
                "\tpid = " + pid + ",\n" +
                "\tprocessFilePath = '" + processFilePath + "',\n" +
                "\tpages = " + pageLines + ",\n" +
                '}';
    }

    private List<String> addPagesToDisk() throws IOException {
        List<ProcessFileLine> lines = ProcessFileParser.getProcessFileLineList(this.processFilePath);
        for(var line: lines) {
            Page page = new Page(
                    line.pageName(),
                    this.pid,
                    line.timeToExecuteInSeconds()
            );
            Disk disk = Disk.getInstance();
            disk.put(line.pageName(), page);
        }

        return lines.stream().map(ProcessFileLine::pageName).toList();
    }

    public void startSimulation(GlobalConfig gc) {
        Cache cache = Cache.getInstance(gc);
        System.out.println(cache);

        // TODO: Get required Pages from cache -> forEachPageName(cache.getPage(pageName))
        // TODO: Run each Page in pageLine-order -> forEachPage(page.run())
    }

}
