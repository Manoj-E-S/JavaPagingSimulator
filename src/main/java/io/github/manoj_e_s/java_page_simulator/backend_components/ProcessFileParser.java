package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessFileParser {

    public static List<ProcessFileLine> getProcessFileLineList(String filePath) throws IOException {
        List<ProcessFileLine> lines = new ArrayList<>();
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
