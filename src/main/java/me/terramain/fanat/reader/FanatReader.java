package me.terramain.fanat.reader;

import me.terramain.fanat.reader.structure.FanatProject;

public class FanatReader {
    public FanatProject fanatProject;
    public FanatReader(String projectPath){
        this.fanatProject = new FanatProject(projectPath);
    }
}
