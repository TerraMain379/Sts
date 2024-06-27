package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;

public class StsBlockFalseNumber extends StsBlock {
    protected int value;

    public StsBlockFalseNumber(StsSaveData saveData, int value) {
        super(saveData);
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return null;
    }

}
