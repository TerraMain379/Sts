package me.terramain.fanat.reader.structure.fanatclass;

public enum FanatClassType {
    CLASS,
    ENUM,
    INTERFACE,
    ANNOTATION;


    public static FanatClassType valueOf(int ID){
        if (ID==0) return CLASS;
        if (ID==1) return ENUM;
        if (ID==2) return INTERFACE;
        if (ID==3) return ANNOTATION;
        return null;
    }
}
