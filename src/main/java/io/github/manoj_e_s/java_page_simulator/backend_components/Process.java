package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Process {

    // process name
    public final String name;

    // pid
    private static int pidCounter = 0;
    public final int pid;

    // process file path
    public final String processFilePath;

    // Its Pages
    private Queue<String> pageLines = new LinkedList<>();


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

    private Queue<String> addAllPages() throws IOException {
        Queue<ProcessFileLine> lines = ProcessFileParser.getProcessFileLineList(this.processFilePath);

        for(var line: lines) {
            Page.createPage(
                    line.pageName(),
                    line.timeToExecuteInSeconds()
            );
        }

        return lines.stream().map(ProcessFileLine::pageName).collect(Collectors.toCollection(LinkedList<String>::new));
    }

    public void startSimulation(GlobalConfig gc) {
        Cache cache = Cache.getInstance(gc);
        System.out.println(cache);

        System.out.println("Running: Process(PID=" + this.pid + ')');
        for(String pageName: this.pageLines){
            Page p = cache.get(pageName);
            p.run();
        }
    }

}
