package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.IOException;
import java.util.Queue;

public class Process {

    // process name
    public final String name;

    // pid
    private static int pidCounter = 0;
    public final int pid;

    // process file path
    public final String processFilePath;

    // Its Pages
    private final Queue<ProcessFileLine> pageLines;


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

        this.pageLines = this.addAllPages();
    }


    // METHODS
    @Override
    public String toString() {
        return "Process {\n" +
                "\tname = '" + name + "',\n" +
                "\tpid = " + pid + ",\n" +
                "\tprocessFilePath = '" + processFilePath + "',\n" +
                "\tpages = Queue" + pageLines + ",\n" +
                '}';
    }

    private Queue<ProcessFileLine> addAllPages() throws IOException {
        Queue<ProcessFileLine> lines = ProcessFileParser.getProcessFileLineList(this.processFilePath);

        for(var line: lines) {
            Page.createPage(line.pageName());
        }

        return lines;
    }

    public void startSimulation() {
        Cache cache = Cache.getInstance();
        System.out.println(cache);

        System.out.println("Running: Process(PID=" + this.pid + ')');
        while(this.pageLines.peek() != null){
            ProcessFileLine pageLine = this.pageLines.poll();
            Page p = cache.get(pageLine.pageName());
            p.run(pageLine.timeToExecuteInSeconds());
        }
    }

}
