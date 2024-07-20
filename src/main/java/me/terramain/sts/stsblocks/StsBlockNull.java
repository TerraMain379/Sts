package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockNull extends StsBlock {
    public StsBlockNull(StsSaveData saveData) {
        super(saveData);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockNull");
        if (saveData!=null) stringBuilder.append(":\n").append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        return stringBuilder.toString();
    }
}
