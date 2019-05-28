package Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {
    public static void wrihtListToFile(List<String> list, String path){
        try {
            FileWriter writer = new FileWriter(path);
            for (String s : list) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {e.printStackTrace();}
        System.out.println("File "+path+" created!");
    }

    public static List<String> getListFromFile(String path) {
        File file = new File(path);
        List <String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null){
                list.add(line);
            }
        } catch (IOException e) {e.printStackTrace();}
        return list;
    }
}
