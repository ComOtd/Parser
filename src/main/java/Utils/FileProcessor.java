package Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class FileProcessor {
    public static void writListToFile(List<String> list, String path) {
        try {
            Files.write(Paths.get(path), list, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File " + path + " created!");
    }
    public static void removeLine(String lineContent, String path) {
        try {
        List<String> out = Files.lines(Paths.get(path))
                .filter(line -> !line.contains(lineContent))
                .collect(Collectors.toList());
        Files.write(Paths.get(path), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


