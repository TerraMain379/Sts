package terramain.sts.description;

import java.util.ArrayList;
import java.util.List;

public class StsDescriptionRules {
    public List<Character> unlockChars;
    public List<Character> startChars;
    public List<Character> endChars;
    public int minChars;
    public int maxChars;


    public static final byte UNLOCK_CHARS = 1;
    public static final byte START_CHARS = 2;
    public static final byte END_CHARS = 3;


    public StsDescriptionRules() {
        this.unlockChars = new ArrayList<>();
        this.startChars = new ArrayList<>();
        this.endChars = new ArrayList<>();
        this.minChars=0;
        this.maxChars=-1;
    }

    public void addUnlockChar(char c){
        for (Character unlockChar : unlockChars) {
            if (c==unlockChar){
                return;
            }
        }
        unlockChars.add(c);
    }
    public void removeUnlockChar(char c){
        unlockChars.remove(c);
    }
    public void addStartChar(char c){
        for (Character unlockChar : startChars) {
            if (c==unlockChar){
                return;
            }
        }
        startChars.add(c);
    }
    public void addEndChar(char c){
        for (Character unlockChar : endChars) {
            if (c==unlockChar){
                return;
            }
        }
        endChars.add(c);
    }

    public void setMinChars(int minChars) {this.minChars = minChars;}
    public void setMaxChars(int maxChars) {this.maxChars = maxChars;}


    public boolean testLimits(int chars){
        return testMinChars(chars)&&testMaxChars(chars);
    }
    public boolean testMinChars(int chars){
        return chars>=minChars;
    }
    public boolean testMaxChars(int chars){
        if (maxChars==-1) return true;
        return chars<=maxChars;
    }

    public boolean testStartWith(char c){
        if (startChars.size()==0) return true;
        return startChars.contains(c);
    }

    public boolean testEndWith(char c){
        if (endChars.size()==0) return true;
        return endChars.contains(c);
    }

    public boolean testChar(char c){
        return unlockChars.contains(c);
    }
}
