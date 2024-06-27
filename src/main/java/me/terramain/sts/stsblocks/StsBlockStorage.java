package me.terramain.sts.stsblocks;


import me.terramain.sts.StsBlocks;
import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.StaticTextEditor;

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
        if (saveData!=null) stringBuilder.append(StaticTextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append(StaticTextEditor.addSpacesToLines(
                stsBlocks.toString(),
                2
        ));
        return stringBuilder.toString();
    }
}
