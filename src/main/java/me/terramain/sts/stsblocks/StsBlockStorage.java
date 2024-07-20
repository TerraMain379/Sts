package me.terramain.sts.stsblocks;


import me.terramain.sts.StsBlocks;
import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockStorage extends StsBlock {
    protected StsBlocks stsBlocks;

    public StsBlockStorage(StsSaveData saveData, StsBlocks stsBlocks) {
        super(saveData);
        this.stsBlocks = stsBlocks;
    }

    public StsBlocks getStsBlocks() {return stsBlocks;}
    public void setStsBlocks(StsBlocks stsBlocks) {this.stsBlocks = stsBlocks;}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockStorage:\n");
        if (saveData!=null) stringBuilder.append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        if (stsBlocks!=null) {
            stringBuilder.append(TextEditor.addSpacesToLines(
                    stsBlocks.toString(),
                    2
            ));
        }
        return stringBuilder.toString();
    }
}
