package me.terramain.fanat.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FanatProject {
    protected String projectPath;
    protected List<FanatFile> files;
    protected List<FanatFile> codeFiles;

    public FanatProject(String projectPath){
        this.projectPath = projectPath;
        for (FanatFile fanatFile : getFilesFromFolder(projectPath)) {
            if (fanatFile.extension.equals("fan")) codeFiles.add(fanatFile);
            else files.add(fanatFile);
        }
    }

    private List<FanatFile> getFilesFromFolder(String folderPath){
        File folder = new File(folderPath);
        List<FanatFile> files = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                files.addAll(getFilesFromFolder(folderPath));
            } else {
                files.add(new FanatFile(file.getAbsolutePath()));
            }
        }
        return files;
    }
}
