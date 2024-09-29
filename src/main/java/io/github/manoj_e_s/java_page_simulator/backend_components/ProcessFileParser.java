package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessFileParser {

    public static Queue<ProcessFileLine> getProcessFileLineList(String filePath) throws IOException {
        Queue<ProcessFileLine> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String readLine;
            while ( (readLine = reader.readLine()) != null ) {
                if( readLine.strip().charAt(0) == '#' ) continue;

                ProcessFileLine line = new ProcessFileLine(
                        readLine.split(" ")[0],
                        Integer.parseInt(readLine.split(" ")[1])
                );

                lines.add(line);
            }
        }
        return lines;
    }

}
