package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.*;
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
    public Process(String processFilePath, String name) {
        this.processFilePath = processFilePath;
        this.name = name;
        this.pid = Process.pidCounter;
        Process.pidCounter++;
    }


    // METHODS
    @Override
    public String toString() {
        return "Process {\n" +
                "\tname = '" + name + "',\n" +
                "\tpid = " + pid + ",\n" +
                "\tprocessFilePath = '" + processFilePath + "',\n" +
                '}';
    }

    public void runProcess() throws IOException {
        InputStream fileStream = this.getClass().getClassLoader().getResourceAsStream("process_files/" + this.processFilePath);
        List<String[]> pagesAndTTE = ProcessFileParser.parseProcessFile(fileStream, "process_files/" + this.processFilePath);

    }

}
