package Utils;

import java.io.*;
import java.util.List;

public class FileProcessor {
    public static void wrihtListToFile(List<String> list, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            for (String s : list) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File " + path + " created!");
    }
}

