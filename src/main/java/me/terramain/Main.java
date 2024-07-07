package me.terramain;

import me.terramain.fanat.Fanat;
import me.terramain.sts.Sts;
import me.terramain.sts.StsReader;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.textexecuter.TextEditor;
import me.terramain.textexecuter.TextHelper;

public class Main {
    public static void main(String[] args) {
        //execute();
        blocks();
    }
    public static void execute(){
        TextEditor stringBuilder = new TextEditor(TextHelper.readFile("src\\main\\resources\\fanat\\sts\\text.txt"));
        stringBuilder.replaceChar('\n',' ');
        String text = stringBuilder.getText();
        String stsCode = TextHelper.readFile("src\\main\\resources\\fanat\\sts\\code.sts");
        for (Result result : Sts.executeText(text, stsCode, true)) {
            System.out.println("---------------------------------------------");
            System.out.println(result);
        }
    }
    public static void blocks(){
        TextEditor stringBuilder = new TextEditor(TextHelper.readFile(Fanat.resources+"\\reader\\sts\\code.sts"));
        stringBuilder.stripLines();
        stringBuilder.removeChar('\n');
        String stsCode = stringBuilder.getText();
        StsReader stsReader = new StsReader(stsCode);
        System.out.println(stsReader.getStsBlocks().toString());
    }
}