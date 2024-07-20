package me.terramain.sts.stsblocks;

import me.terramain.sts.StsBlocks;
import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockOptions extends StsBlockStorage{
    public StsBlockOptions(StsSaveData saveData, StsBlocks stsBlocks) {
        super(saveData, stsBlocks);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockOptions:\n");
        if (saveData!=null) stringBuilder.append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append(TextEditor.addSpacesToLines(
                stsBlocks.toString(),
                2
        ));
        return stringBuilder.toString();
    }
}
