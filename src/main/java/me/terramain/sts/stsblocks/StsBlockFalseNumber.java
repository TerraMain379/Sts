package terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;

public class StsBlockFalseNumber extends StsValueBlock {
    public StsBlockFalseNumber(StsSaveData saveData, int value) {
        super(saveData, value);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int getValue() {
        return value;
    }
}
