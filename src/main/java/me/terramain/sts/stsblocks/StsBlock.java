package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;

public abstract class StsBlock {
    protected StsSaveData saveData;

    public StsBlock(StsSaveData saveData) {
        this.saveData = saveData;
    }

    public StsSaveData getSaveData() {return saveData;}
    public void setSaveData(StsSaveData saveData) {this.saveData = saveData;}

    @Override
    public abstract String toString();/*{
        StringBuilder stringBuilder = new StringBuilder("StsBlock:\n");
        if (saveData!=null) stringBuilder.append(StaticTextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        return stringBuilder.toString();
    }*/
}
