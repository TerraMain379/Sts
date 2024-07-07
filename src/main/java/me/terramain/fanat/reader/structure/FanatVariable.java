package me.terramain.fanat.reader.structure;

import me.terramain.fanat.reader.structure.link.FanatLink;

public class FanatVariable {
    public String name;
    public FanatLink type;
    public FanatSecLevel secLevel;
    public boolean isStatic;

    public FanatVariable(String name, FanatLink type, FanatSecLevel secLevel, boolean isStatic) {
        this.name = name;
        this.type = type;
        this.secLevel = secLevel;
        this.isStatic = isStatic;
    }
}
