package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.TextEditor;

public class StsBlockText extends StsBlock{
    protected StringBuilder stringBuilder;

    public StsBlockText(StsSaveData saveData, String text) {
        super(saveData);
        this.stringBuilder = new StringBuilder(text);
    }

    public StsBlockText(StsSaveData saveData, StringBuilder stringBuilder) {
        super(saveData);
        this.stringBuilder = stringBuilder;
    }

    public String getText() {
        return stringBuilder.toString();
    }

    public void setText(String text) {
        this.stringBuilder = new StringBuilder(text);
    }

    public void addText(String text) {
        this.stringBuilder.append(text);
    }
    public void addChar(char c) {
        this.stringBuilder.append(c);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockText:\n");
        if (saveData!=null) stringBuilder.append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append("  text:").append(getText()).append('\n');
        return stringBuilder.toString();
    }
}
