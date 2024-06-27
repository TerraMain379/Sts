package me.terramain;

import me.terramain.sts.Sts;
import me.terramain.sts.StsReader;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.textexecuter.TextEditor;
import me.terramain.textexecuter.TextHelper;

public class Main {
    public static void main(String[] args) {
        execute();
        //blocks();
    }
    public static void execute(){
        TextEditor stringBuilder = new TextEditor(TextHelper.readFile("sts.txt"));
        stringBuilder.replaceChar('\n',' ');
        String text = stringBuilder.getText();
        stringBuilder = new TextEditor(TextHelper.readFile("sts2.txt"));
        stringBuilder.stripLines();
        stringBuilder.removeChar('\n');
        String stsCode = stringBuilder.getText();
        for (Result result : Sts.executeText(text, stsCode)) {
            System.out.println("---------------------------------------------");
            System.out.println(result);
        }
    }
    public static void blocks(){
        TextEditor stringBuilder = new TextEditor(TextHelper.readFile("sts2.txt"));
        stringBuilder.stripLines();
        stringBuilder.removeChar('\n');
        String stsCode = stringBuilder.getText();
        StsReader stsReader = new StsReader(stsCode);
        System.out.println(stsReader.getStsBlocks().toString());
    }
}