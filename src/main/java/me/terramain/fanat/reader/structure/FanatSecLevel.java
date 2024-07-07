package me.terramain.fanat.reader.structure;

public enum FanatSecLevel {
    PUBLIC,
    PRIVATE;

    public static FanatSecLevel valueOf(int ID){
        if (ID==0) return PUBLIC;
        if (ID==1) return PRIVATE;
        return null;
    }
}
