package io.github.manoj_e_s.java_page_simulator.backend_components;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessFileParser {
    public static List<String[]> parseProcessFile(InputStream fileStream, String filePath) throws IOException {
        if (fileStream == null) {
            throw new FileNotFoundException("Process File not found: " + filePath);
        }

        List<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream))) {
            String[] line;
            String readLine;
            while ((readLine = reader.readLine()) != null) {
                line = readLine.split(" ");
                System.out.println(Arrays.toString(line));
                lines.add(line);
            }
            System.out.println(lines);
        }
        return lines;
    }
}
