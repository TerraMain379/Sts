package me.terramain.fanat.reader;

public class FanatReader {
    public FanatReader(String projectPath){
        FanatProject fanatProject = new FanatProject(projectPath);
        FanatCode fanatCode = new FanatCode();
        for (FanatFile codeFile : fanatProject.codeFiles) {
            fanatCode.addCode(codeFile.read());
        }
        fanatCode.execute();
    }
}
