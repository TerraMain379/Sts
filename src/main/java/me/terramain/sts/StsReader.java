package me.terramain.sts;

import me.terramain.sts.exceptions.StsException;
import me.terramain.sts.stsblocks.*;
import me.terramain.textexecuter.TextIterator;

import java.util.HashMap;
import java.util.Map;

public class StsReader {
    private final StsBlocks stsBlocks;
    private Map<String,StsBlocks> schemes;
    public StsReader(String text){
        TextIterator textIterator = new TextIterator(text);
        this.schemes = new HashMap<>();
        this.stsBlocks = readBlocks(textIterator);
    }
    private StsReader(String text, Map<String,StsBlocks> schemes){
        TextIterator textIterator = new TextIterator(text);
        this.schemes = schemes;
        this.stsBlocks = readBlocks(textIterator);
    }

    private StsBlocks readBlocks(TextIterator textIterator){
        StsBlocks stsBlocks = new StsBlocks();
        boolean flag = true;
        while (flag){
            if (!textIterator.hasNextAt(0)){
                flag=false;
            }
            else if (textIterator.getChar()==')' || textIterator.getChar()=='}'){
                flag = false;
                textIterator.next();
            }
            else {
                StsBlock stsBlock = readBlock(textIterator);
                if (stsBlock!=null) stsBlocks.add(stsBlock);
            }
        }
        return stsBlocks;
    }
    private StsBlock readBlock(TextIterator textIterator){
        char c = textIterator.getChar();

        StsSaveData saveData = null;
        if (c=='_'){
            saveData=readSaveData(textIterator);
            c = textIterator.getChar();
        }

        if (c=='('){
            textIterator.next();
            return new StsBlockStorage(saveData,readBlocks(textIterator));
        }
        else if (c=='|'){
            textIterator.nextAt(2);
            return new StsBlockOptions(saveData,readBlocks(textIterator));
        }
        else if (c=='?'){
            textIterator.nextAt(2);
            return new StsBlockPossible(saveData,readBlocks(textIterator));
        }
        else if (c=='*'){
            StsBlock loopCounterBlock = null;
            if (textIterator.next() != '{') loopCounterBlock = readBlock(textIterator);
            textIterator.nextAt(1);
            return new StsBlockRepeat(saveData,readBlocks(textIterator),loopCounterBlock);
        }
        else if (c=='{'){
            StringBuilder stringBuilder = new StringBuilder();
            while (textIterator.hasNext()){
                c = textIterator.next();
                if (c=='$') stringBuilder.append('$').append(textIterator.next());
                else if (c=='}') break;
                else stringBuilder.append(c);
            }
            textIterator.next();
            return new StsBlockCloseCode(saveData,stringBuilder.toString());
        }
        else if (c=='"'){
            boolean flag = true;
            StsBlockText stsBlockText = new StsBlockText(saveData,"");
            textIterator.next();
            while (flag){
                c = textIterator.getChar();
                if (c=='\\'){
                    c = textIterator.next();
                    stsBlockText.addChar(Sts.getSystemChar(c));
                }
                else if (c=='"'){
                    flag = false;
                }
                else {
                    stsBlockText.addChar(c);
                }

                textIterator.next();
            }
            return stsBlockText;
        }
        else if (c=='['){
            boolean flag = true;
            StsBlockDescription stsBlockDescription = new StsBlockDescription(saveData,"");
            textIterator.next();
            while (flag){
                c = textIterator.getChar();
                if (c=='\\'){
                    stsBlockDescription.addChar(c);
                    stsBlockDescription.addChar(textIterator.next());
                }
                else if (c==']'){
                    flag=false;
                }
                else {
                    stsBlockDescription.addChar(c);
                }
                textIterator.next();
            }
            return stsBlockDescription;
        }
        else if (c=='/'){
            StringBuilder nameBuilder = new StringBuilder();
            StringBuilder codeBuilder = null;
            boolean flag = true;
            while (flag){
                if (textIterator.next()==null) break;
                c = textIterator.getChar();
                if (c=='/') {
                    flag=false;
                    textIterator.next();
                }
                else if (c=='{') {
                    codeBuilder = new StringBuilder();
                    int level = 1;
                    boolean inQuotation = false;
                    while (true){
                        c = textIterator.next();
                        if (c=='\\'){
                            codeBuilder.append(c);
                            c = textIterator.next();
                        }
                        else if (c=='"'){
                            inQuotation=!inQuotation;
                        }
                        else if (!inQuotation){
                            if (c=='{') level++;
                            else if (c=='}') level--;
                            if (level<=0) break;
                        }
                        codeBuilder.append(c);
                    }
                }
                else nameBuilder.append(c);
            }
            if (codeBuilder==null){
                try {
                    return new StsBlockStorage(saveData, schemes.get(nameBuilder.toString()));
                } catch (NullPointerException e) {
                    Sts.exception("scheme not found.");
                }
            }
            else {
                StsReader stsReader = new StsReader(codeBuilder.toString(),schemes);
                schemes.put(nameBuilder.toString(),stsReader.stsBlocks);
                return null;
            }
        }
        else if (c=='N'){
            textIterator.next();
            return new StsBlockNull(saveData);
        }
        else if (c=='$'){
            return new StsBlockLink(saveData,readSaveData(textIterator));
        }
        else if (c=='@'){
            StringBuilder stringBuilder = new StringBuilder();
            while (textIterator.hasNext()){
                if (textIterator.next()==';') break;
                stringBuilder.append(textIterator.getChar());
            }
            textIterator.next();
            return new StsBlockSystem(saveData,stringBuilder.toString());
        }
        else if (c=='`'){
            while (textIterator.hasNext()){
                if (textIterator.next()=='`') break;
            }
            textIterator.next();
            return null;
        }
        else if (Character.isDigit(c)){
            return new StsBlockFalseNumber(saveData,textIterator.readInt());
        }
        else {
            StsException.say(
                    "Wrong char (" +
                            textIterator.getPreviousAt(6)+
                            textIterator.getPreviousAt(5)+
                            textIterator.getPreviousAt(4)+
                            textIterator.getPreviousAt(3)+
                            textIterator.getPreviousAt(2)+
                            textIterator.getPreviousAt(1)+
                            "'" + c + "'" +
                            textIterator.getNextAt(1) +
                            textIterator.getNextAt(2) +
                            textIterator.getNextAt(1) +
                            ") from sts-code. Position " +
                            textIterator.getIndex()
            );
        }
        return null;
    }
    private StsSaveData readSaveData(TextIterator textIterator){
        int registryNum = 0;
        int valueNum = -1;

        if (textIterator.next() != '<'){
            return new StsSaveData(registryNum,valueNum);
        }
        else {
            if (Character.isDigit(textIterator.next())){
                registryNum=textIterator.readInt();
            }
            else if (textIterator.getChar()!=':'){
                registryNum = StsSaveData.getPathFromChar(textIterator.getChar());
                textIterator.next();
            }

            if (textIterator.getChar()==':'){
                if (textIterator.next() != '>') {
                    if (Character.isDigit(textIterator.getChar())){
                        valueNum=textIterator.readInt();
                        textIterator.next();
                    }
                    else {
                        valueNum = StsSaveData.getPathFromChar(textIterator.getChar());
                        textIterator.nextAt(2);
                    }
                }
                else {
                    textIterator.next();
                }
            }
            else {
                valueNum = registryNum;
                registryNum = 0;
            }
        }
        return new StsSaveData(registryNum,valueNum);
    }
    public StsBlocks getStsBlocks() {
        return stsBlocks;
    }

}
