package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.StaticTextEditor;

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
        StringBuilder stringBuilder = new StringBuilder("StsBlockFalseNumber:\n");
        if (saveData!=null) stringBuilder.append(StaticTextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append("  value:").append(getValue()).append('\n');
        return stringBuilder.toString();
    }

}
