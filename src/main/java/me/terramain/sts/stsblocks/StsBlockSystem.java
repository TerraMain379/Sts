package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockSystem extends StsBlock{
    private final String type;
    public StsBlockSystem(StsSaveData saveData, String type) {
        super(saveData);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockText:\n");
        if (saveData!=null) stringBuilder.append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append("  type:").append(getType()).append('\n');
        return stringBuilder.toString();
    }
}
