package me.terramain.fanat.reader.structure;

import me.terramain.fanat.reader.StsLogic;
import me.terramain.fanat.reader.structure.fanatclass.FanatClass;
import me.terramain.fanat.reader.structure.fanatclass.FanatClassType;
import me.terramain.fanat.reader.structure.link.FanatLink;
import me.terramain.fanat.reader.structure.link.FanatLinkType;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.textexecuter.StaticTextEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FanatProject {
    public final String projectPath;
    public List<FanatFile> files;
    public List<FanatFile> codeFiles;

    public List<FanatClass> fanatClasses;

    public FanatProject(String projectPath) {
        this.projectPath = projectPath;
        loadFiles();
        readCode();
    }
    private void loadFiles(){
        files = new ArrayList<>();
        codeFiles = new ArrayList<>();
        for (File file : loadFiles(projectPath)) {
            FanatFile loadFile = new FanatFile(file.getPath());
            if (loadFile.getExpansion().equals("fan")) codeFiles.add(loadFile);
            else files.add(loadFile);
        }
    }
    private List<File> loadFiles(String path){
        List<File> files = new ArrayList<>();
        for (File file : new File(path).listFiles()) {
            if (file.isDirectory()) {
                files.addAll(loadFiles(file.getPath()));
            } else {
                files.add(file);
            }
        }
        return files;
    }

    public void readCode(){
        StringBuilder codeBuilder = new StringBuilder();
        codeFiles.forEach(fanatFile -> codeBuilder.append(fanatFile.read()));
        String code = codeBuilder.toString();

        fanatClasses = new ArrayList<>();
        Result codeReadResult = StsLogic.executeSts(code,"code");
        if (codeReadResult==null) return;
        codeReadResult.getResultRegisties().foreach(resultRegistry -> {
            FanatClassType fanatClassType = FanatClassType.valueOf(resultRegistry.getElement(0).getInt());
            String name = resultRegistry.getElement(1).getString();
            FanatLink parent = null;
            if (!resultRegistry.getElement(2).isNull()){
                parent = new FanatLink(
                        resultRegistry.getElement(2).getString(),
                        FanatLinkType.CLASS
                );
            }
            FanatLink implemented = null;
            if (!resultRegistry.getElement(3).isNull()){
                implemented = new FanatLink(
                        resultRegistry.getElement(3).getString(),
                        FanatLinkType.CLASS
                );
            }
            fanatClasses.add(FanatClass.generate(
                    name, parent, implemented,
                    resultRegistry.getElement(4).getString(),
                    fanatClassType
            ));
        });

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("FanatProject: \n");
        builder.append("  projectPath: ").append(projectPath).append("\n");
        builder.append("  files:\n");
        for (FanatFile file : files) {
            builder.append("    ").append(file.getPath()).append("\n");
        }
        builder.append("  codeFiles:\n");
        for (FanatFile file : codeFiles) {
            builder.append("    ").append(file.getPath()).append("\n");
        }
        builder.append("  classes:\n");
        for (FanatClass fanatClass : fanatClasses) {
            builder.append(
                    StaticTextEditor.addSpacesToLines(
                            fanatClass.toString(),
                            4
                    )
            );
        }
        return builder.toString();
    }
}
