package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    public String read(String filePath) {
        String text = "";

        try {
            Stream<String> line = Files.lines(Path.of(filePath));
            text = line.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}