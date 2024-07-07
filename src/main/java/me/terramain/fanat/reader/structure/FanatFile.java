package me.terramain.fanat.reader.structure;

import me.terramain.textexecuter.TextHelper;

import java.io.File;

public class FanatFile {
    public String path;

    public FanatFile(String path) {
        this.path = path;
    }

    public String read(){
        return TextHelper.readFile(path);
    }

    public String getPath() {
        return path;
    }
    public String getExpansion(){
        File file = new File(path);
        return file.getName().split("\\.")[
                    file.getName().split("\\.").length-1
                ];
    }

    @Override
    public String toString() {
        return "FanatFile{" +
                "path='" + path + '\'' +
                '}';
    }
}
