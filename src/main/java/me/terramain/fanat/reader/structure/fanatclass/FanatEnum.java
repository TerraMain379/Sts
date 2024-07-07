package me.terramain.fanat.reader.structure.fanatclass;

import me.terramain.fanat.reader.structure.link.FanatLink;

public class FanatEnum extends FanatClass{
    public String[] enumVariables;

    public FanatEnum(String name, FanatLink parent, FanatLink implemented, String code) {
        super(name, parent, implemented, code);
    }


    @Override
    public String toString() {
        return "FanatEnum: \n" + "  name: " + name + "\n" +
                "  parent: " + parent + "\n" +
                "  implemented: " + implemented + "\n";
    }
}
