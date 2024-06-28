package me.terramain.textexecuter;

import java.io.*;

public class TextHelper {
    public static String readFile(File file){
        BufferedReader bufferedReader;
        try {
             bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        bufferedReader.lines().forEach(s -> {
            stringBuilder.append(s).append("\n");
        });

        try {
            bufferedReader.close();
        } catch (IOException e) {throw new RuntimeException(e);}
        return stringBuilder.toString();
    }
    public static String readFile(String path){
        return readFile(new File(path));
    }
}
