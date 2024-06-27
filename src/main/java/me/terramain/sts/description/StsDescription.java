package terramain.sts.description;

import me.terramain.sts.Sts;
import me.terramain.textexecuter.TextIterator;

import java.util.ArrayList;
import java.util.List;

public class StsDescription {
    private String text;
    private StringBuilder stringBuilder;
    private StsDescriptionRules descriptionRules;

    public StsDescription() {
        this("");
    }
    public StsDescription(String text) {
        setText(text);
        descriptionRules=new StsDescriptionRules();
    }


    public String getText() {
        text = stringBuilder.toString();
        return text;
    }

    public void setText(String text) {
        this.text=text;
        stringBuilder = new StringBuilder(text);
    }

    public void addText(String text) {
        stringBuilder.append(text);
    }

    public void addChar(char c) {
        stringBuilder.append(c);
    }


    public StsDescriptionRules readRules(){
        TextIterator textIterator = new TextIterator(getText());
        boolean flag = true;
        while (flag){
            char c = textIterator.getChar();
            if (c=='+'){
                textIterator.next();
                char[] chars = readRuleChars(textIterator);
                for (char aChar : chars) {
                    descriptionRules.addUnlockChar(aChar);
                }
            }
            else if (c=='-'){
                textIterator.next();
                char[] chars = readRuleChars(textIterator);
                for (char aChar : chars) {
                    descriptionRules.removeUnlockChar(aChar);
                }
            }
            else if (c=='>') {
                textIterator.next();
                char[] chars = readRuleChars(textIterator);
                for (char aChar : chars) {
                    descriptionRules.addStartChar(aChar);
                }
            }
            else if (c=='<') {
                textIterator.next();
                char[] chars = readRuleChars(textIterator);
                for (char aChar : chars) {
                    descriptionRules.addStartChar(aChar);
                }
            }
            else if (c=='^') {
                if (textIterator.next()=='>'){
                    textIterator.next();
                    descriptionRules.setMinChars(textIterator.readInt());
                }
                else if (textIterator.getChar()=='<'){
                    textIterator.next();
                    descriptionRules.setMaxChars(textIterator.readInt());
                }
                else {
                    int num = textIterator.readInt();
                    descriptionRules.setMaxChars(num);
                    descriptionRules.setMinChars(num);
                }
            }
            if (textIterator.getChar()==null) flag = false;
        }
        return this.descriptionRules;
    }

    private char[] readRuleChars(TextIterator textIterator){
        StringBuilder stringBuilder = new StringBuilder();
        boolean flag = true;
        while (flag){
            char c = textIterator.getChar();
            if (c=='\\'){
                c = textIterator.next();
                char sysC = Sts.getSystemChar(c);
                if (sysC!=0) stringBuilder.append(sysC);
                else {
                    for (Character customSystemChar : getCustomSystemChars(c)) {
                        stringBuilder.append(customSystemChar);
                    }
                }
            }
            else {
                for (char testC:"+-<>^[]'`\"!".toCharArray()){
                    if (testC==c){
                        flag=false;
                        break;
                    }
                }
                if (flag) stringBuilder.append(c);
            }
            if (flag) textIterator.next();
            if (textIterator.getChar()==null) flag = false;
        }
        return stringBuilder.toString().toCharArray();
    }
    private List<Character> getCustomSystemChars(char c){
        char[] chars = new char[0];
        if (c=='d') chars = getAllDefChars();
        if (c=='a') chars = getAllSystemChars(127);
        if (c=='A') chars = getAllSystemChars(65535);
        if (c=='e') chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        if (c=='E') chars = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        if (c=='l') {
            chars = (
                    "abcdefghijklmnopqrstuvwxyz".toUpperCase()+
                            "abcdefghijklmnopqrstuvwxyz"
            ).toCharArray();
        }
        if (c=='v') chars = " \t\n".toCharArray();
        if (c=='N') chars = "1234567890".toCharArray();

        List<Character> characters = new ArrayList<>();
        for (char aChar : chars) {
            characters.add(aChar);
        }
        return characters;
    }


    private static char[] getAllSystemChars(int max){
        char[] cs = new char[max+1];
        for (int i = 0; i <= max; i++) {
            cs[i]=(char)i;
        }
        return cs;
    }

    private static char[] getAllDefChars(){
        char[] cs = new char[104];
        for (int i = 28; i < 127; i++) {
            cs[i]=(char)i;
        }
        cs[103]=' ';//" \t\n\f\r".toCharArray()
        cs[102]='\t';
        cs[101]='\n';
        cs[100]='\f';
        cs[99]='\r';
        return cs;

    }
}
