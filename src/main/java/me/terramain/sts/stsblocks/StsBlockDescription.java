package me.terramain.sts.stsblocks;

import me.terramain.sts.StsSaveData;
import me.terramain.sts.description.StsDescription;
import me.terramain.textexecuter.TextEditor;

public class StsBlockDescription extends StsBlock{
    protected StsDescription stsDescription;

    public StsBlockDescription(StsSaveData saveData, StsDescription stsDescription) {
        super(saveData);
        this.stsDescription = stsDescription;
    }

    public StsBlockDescription(StsSaveData saveData, String text) {
        super(saveData);
        this.stsDescription = new StsDescription(text);
    }

    public String getText() {
        return stsDescription.getText();
    }
    public void setText(String text) {
        stsDescription.setText(text);
    }
    public void addText(String text) {
        stsDescription.addText(text);
    }
    public void addChar(char c) {
        stsDescription.addChar(c);
    }

    public StsDescription getStsDescription() {return stsDescription;}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("StsBlockDescription:\n");
        if (saveData!=null) stringBuilder.append(TextEditor.addSpacesToLines(
                saveData.toString(),
                2
        ));
        stringBuilder.append("  text:").append(getText()).append('\n');
        return stringBuilder.toString();
    }
}
