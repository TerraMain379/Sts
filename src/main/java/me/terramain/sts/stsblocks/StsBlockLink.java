package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockLink extends StsBlock{
    protected StsSaveData link;

    public StsBlockLink(StsSaveData saveData, StsSaveData link) {
        super(saveData);
        this.link = link;
    }

    public StsSaveData getLink() {
        return link;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockLink:\n");
        if (saveData!=null) stringBuilder.append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append("  link:").append(TextEditor.addSpacesToLines(
                link.toString(),
                4
        ));
        return stringBuilder.toString();
    }
}
