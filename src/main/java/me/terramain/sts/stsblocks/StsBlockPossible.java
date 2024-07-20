package me.terramain.sts.stsblocks;

import me.terramain.sts.StsBlocks;
import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockPossible extends StsBlockStorage{
    public StsBlockPossible(StsSaveData saveData, StsBlocks stsBlocks) {
        super(saveData, stsBlocks);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockPossible:\n");
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
