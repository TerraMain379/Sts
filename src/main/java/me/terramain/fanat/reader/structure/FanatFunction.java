package me.terramain.fanat.reader.structure;

import me.terramain.fanat.reader.structure.link.FanatLink;

import java.util.Collections;
import java.util.List;

public class FanatFunction {
    public String name;
    public FanatSecLevel secLevel;
    public boolean isStatic;
    public FanatLink returnType;

    public List<FanatLink> args;
    public FanatCode fanatCode;

    public FanatFunction(String name, FanatSecLevel secLevel, boolean isStatic, FanatLink returnType, List<FanatLink> args, FanatCode fanatCode) {
        this.name = name;
        this.secLevel = secLevel;
        this.isStatic = isStatic;
        this.returnType = returnType;
        this.args = args;
        this.fanatCode = fanatCode;
    }
    public FanatFunction(String name, FanatSecLevel secLevel, boolean isStatic, FanatLink returnType) {
        this.name = name;
        this.secLevel = secLevel;
        this.isStatic = isStatic;
        this.returnType = returnType;
    }

    public void addParam(FanatLink fanatLink){
        args.add(fanatLink);
    }
    public void addParams(FanatLink... fanatLinks){
        Collections.addAll(args, fanatLinks);
    }
}
