package me.terramain.fanat;

public enum FanSecurityLevel {
    PUBLIC(0),
    PRIVATE(1),
    PROTECTED(2);

    public final int id;
    FanSecurityLevel(int id) {
        this.id=id;
    }


    public FanSecurityLevel getById(int id) {
        for (FanSecurityLevel value : FanSecurityLevel.values()) {
            if (value.id==id) return value;
        }
        return null;
    }
}
