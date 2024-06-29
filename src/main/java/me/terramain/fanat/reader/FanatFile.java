package me.terramain.fanat.reader;

import me.terramain.textexecuter.TextHelper;

import java.io.File;

public class FanatFile {
    private String path;
    private File file;
    private String name;
    private String extension;

    public FanatFile(String path) {
        this.path = path;
        this.file = new File(path);
        this.name = file.getName();
        this.extension = name.split("\\.")[name.split("\\.").length-1];
        this.name = name.substring(0,name.length()-1-extension.length());
    }

    public String read(){
        return TextHelper.readFile(file);
    }

    public String getPath() {return path;}
    public File getFile() {return file;}
    public String getName() {return name;}
    public String getExtension() {return extension;}
}
