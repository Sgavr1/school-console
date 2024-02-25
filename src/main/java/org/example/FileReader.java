package org.example;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileReader {
    public String read(String filePath) {
        try (Stream<String> line = Files.lines(Path.of(filePath))) {

            return line.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}