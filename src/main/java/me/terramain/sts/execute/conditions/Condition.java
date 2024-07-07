package me.terramain.sts.execute.conditions;

import me.terramain.sts.StsBlocks;
import me.terramain.sts.StsSaveData;
import me.terramain.sts.description.StsDescriptionRules;
import me.terramain.sts.exceptions.StsException;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.sts.execute.regesties.ResultRegistryValue;
import me.terramain.sts.execute.regesties.ResultRegistryValueType;
import me.terramain.sts.stsblocks.*;
import me.terramain.textexecuter.TextIterator;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    private StsBlock stsBlock;
    private StsBlock nextStsBlock;
    private final TextIterator textIterator;
    private final Result result;

    public Condition(StsBlock stsBlock, TextIterator textIterator, Result result) {
        this.stsBlock = stsBlock;
        this.textIterator = textIterator.clone();
        this.result = result.clone();
    }

    public List<Condition> execute(StsBlock nextStsBlock){
        this.nextStsBlock = nextStsBlock;

        if (stsBlock instanceof StsBlockText stsBlockText){
            if (stsBlock instanceof StsBlockCloseCode stsBlockCloseCode){
                return executeBlockCloseCode(stsBlockCloseCode);
            }
            else {
                return executeBlockText(stsBlockText);
            }
        }
        else if (stsBlock instanceof StsBlockStorage stsBlockStorage){
            if (stsBlockStorage instanceof StsBlockPossible stsBlockPossible){
                return executeBlockPossible(stsBlockPossible);
            }
            else if (stsBlockStorage instanceof StsBlockOptions stsBlockOptions){
                return executeBlockOptions(stsBlockOptions);
            }
            else if (stsBlockStorage instanceof StsBlockRepeat stsBlockRepeat){
                return executeBlockRepeat(stsBlockRepeat);
            }
            else {
                return executeBlockStorage(stsBlockStorage);
            }
        }
        else if (stsBlock instanceof StsBlockDescription stsBlockDescription){
            return executeBlockDescription(stsBlockDescription);
        }
        else if (stsBlock instanceof StsBlockFalseNumber stsBlockFalseNumber){
            return executeBlockFalseNumber(stsBlockFalseNumber);
        }
        else if (stsBlock instanceof StsBlockNull stsBlockNull){
            return executeBlockNull(stsBlockNull);
        }
        else if (stsBlock instanceof StsBlockLink stsBlockLink){
            return executeBlockLink(stsBlockLink);
        }
        else if (stsBlock instanceof StsBlockSystem stsBlockSystem){
            return executeBlockSystem(stsBlockSystem);
        }
        else {
            //System.out.println("StsBlock not supported");
            StsException.say("StsBlock not supported");
        }

        return List.of(new Condition(nextStsBlock, textIterator, result));
    }

    private List<Condition> executeBlockText(StsBlockText stsBlockText){
        String blockText = stsBlockText.getText();
        String readText = textIterator.readStringFromLength(blockText.length());
        if (blockText.equals(readText)){
            result.executeSaveData(stsBlockText.getSaveData(),new ResultRegistryValue(readText));
            return List.of(
                    new Condition(nextStsBlock, textIterator, result)
            );
        }
        result.setSuccess(false);
        return new ArrayList<>();
    }
    private List<Condition> executeStsBlocks(StsBlocks stsBlocks){
        ConditionsLine conditionsLine = new ConditionsLine(
                stsBlocks,
                new ArrayList<>(),
                textIterator
        );
        conditionsLine.execute(result);
        List<Condition> newConditions = new ArrayList<>();
        conditionsLine.getConditions().forEach(condition -> newConditions.add(new Condition(
                nextStsBlock,condition.textIterator,condition.result
        )));
        return newConditions;
    }
    private List<Condition> executeBlockStorage(StsBlockStorage stsBlockStorage){
        int startResultNum = textIterator.getCharNumber();

        List<Condition> newConditions = executeStsBlocks(stsBlockStorage.getStsBlocks());

        if (stsBlockStorage.getSaveData()!=null){
            newConditions.forEach(newCondition -> {
                int endResultNum = newCondition.textIterator.getCharNumber();
                newCondition.result.executeSaveData(
                        stsBlockStorage.getSaveData(),
                        new ResultRegistryValue(
                                textIterator.getSubstring(startResultNum,endResultNum)
                        )
                );
            });
        }
        return newConditions;
    }
    private List<Condition> executeBlockPossible(StsBlockPossible stsBlockPossible){
        List<Condition> newConditions = new ArrayList<>();
        newConditions.add(new Condition(nextStsBlock,textIterator,result));
        newConditions.addAll(executeStsBlocks(stsBlockPossible.getStsBlocks()));

        if (stsBlockPossible.getSaveData()!=null){
            boolean flag = false;
            for (Condition newCondition : newConditions) {
                newCondition.result.executeSaveData(stsBlock.getSaveData(),new ResultRegistryValue(flag));
                flag=true;
            }
        }
        return newConditions;
    }
    private List<Condition> executeBlockOptions(StsBlockOptions stsBlockOptions){

        List<ConditionsLine> conditionsLines = new ArrayList<>();
        stsBlockOptions.getStsBlocks().forEach(stsBlock -> conditionsLines.add(new ConditionsLine(
                new StsBlocks(List.of(stsBlock)),
                textIterator.clone()
        )));
        List<Condition> newConditions = new ArrayList<>();
        int conditionsLineNumber = 0;
        for (ConditionsLine conditionsLine : conditionsLines) {
            conditionsLine.execute(result.clone());
            for (Condition condition : conditionsLine.getConditions()) {
                Condition newCondition = new Condition(
                        nextStsBlock,
                        condition.textIterator,
                        condition.result
                );
                newCondition.result.executeSaveData(
                        stsBlockOptions.getSaveData(),
                        new ResultRegistryValue(conditionsLineNumber)
                );
                newCondition.result.setLoopsMessage(
                        conditionsLineNumber
                );
                newConditions.add(newCondition);
            }
            conditionsLineNumber++;
        }

        if (stsBlockOptions.getSaveData()!=null){
            newConditions.forEach(condition -> {

            });
        }
        return newConditions;
    }
    private List<Condition> executeBlockRepeat(StsBlockRepeat stsBlockRepeat){
        StsBlock stsBlockCounter = stsBlockRepeat.getLoopCounterBlock();
        if (stsBlockCounter==null) {
            return executeBlockRepeat(stsBlockRepeat, false, 0);
        }
        else {
            ConditionsLine conditionsLine = new ConditionsLine(
                    new StsBlocks(List.of(stsBlockCounter)),
                    textIterator
            );
            conditionsLine.execute(result);
            List<Condition> newConditions = new ArrayList<>();
            for (Condition condition : conditionsLine.conditions) {
                Integer loopsCounter = condition.result.getLoopsMessage();
                if (condition.result.getLoopsMessage() != null) {
                    newConditions.addAll(
                            condition.executeBlockRepeat(
                                    stsBlockRepeat, true, loopsCounter
                            )
                    );
                }
            }
            newConditions.forEach(condition -> condition.stsBlock=nextStsBlock);
            return newConditions;
        }
    }
    private List<Condition> executeBlockRepeat(StsBlockRepeat stsBlockRepeat, boolean activeLoopsCounter, int loopsCounter){
        List<Condition> newConditions = new ArrayList<>();
        List<Condition> conditionsToNext = new ArrayList<>();
        conditionsToNext.add(new Condition(stsBlock,textIterator,result));

        boolean flag = true;
        int loops = 1;
        while (flag){
            List<ConditionsLine> conditionsLines = new ArrayList<>();
            if (conditionsToNext.size()==0){
                if (!activeLoopsCounter || loops==loopsCounter) {
                    flag=false;
                }
            }
            else if (activeLoopsCounter && loops>loopsCounter){
                flag=false;
            }
            else {
                for (Condition condition : conditionsToNext) {
                    ConditionsLine conditionsLine = new ConditionsLine(
                            stsBlockRepeat.getStsBlocks(),
                            condition.getTextIterator()
                    );
                    conditionsLine.execute(condition.result);
                    conditionsLines.add(conditionsLine);
                }
                conditionsToNext.clear();
                List<Condition> conditionsToNew = new ArrayList<>();
                for (ConditionsLine conditionsLine : conditionsLines) {
                    for (Condition conditionToNext : conditionsLine.getConditions()) {
                        Condition conditionToNew = new Condition(
                                conditionToNext.stsBlock,
                                conditionToNext.textIterator,
                                conditionToNext.result
                        );
                        conditionToNew.result.executeSaveData(
                                stsBlockRepeat.getSaveData(),
                                new ResultRegistryValue(loops)
                        );
                        conditionToNew.result.setLoopsMessage(
                                loops
                        );
                        conditionsToNext.add(conditionToNext);
                        conditionsToNew.add(conditionToNew);
                    }
                }
                if (!activeLoopsCounter || loops == loopsCounter) {
                    newConditions.addAll(conditionsToNew);
                }
            }
            loops++;
        }
        newConditions.forEach(condition -> condition.stsBlock=nextStsBlock);
        return newConditions;
    }
    private List<Condition> executeBlockDescription(StsBlockDescription stsBlockDescription){

        List<Condition> newConditions = new ArrayList<>();

        StsDescriptionRules stsDescriptionRules = stsBlockDescription.getStsDescription().readRules();

        boolean flag = true;
        int charNum = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (flag){
            if (textIterator.getChar()==null) break;
            char c = textIterator.getChar();

            if (charNum==0 && !stsDescriptionRules.testStartWith(c)) flag = false;
            else if (!stsDescriptionRules.testChar(c)) flag = false;
            else if (!stsDescriptionRules.testMaxChars(charNum+1)) flag = false;
            else if (stsDescriptionRules.testMinChars(charNum+1)){
                stringBuilder.append(c);
                textIterator.next();
                Condition newCondition = new Condition(nextStsBlock,textIterator,result);
                if (stsDescriptionRules.isNumber) {
                    int value = Integer.parseInt(stringBuilder.toString());
                    newCondition.result.executeSaveData(
                            stsBlockDescription.getSaveData(),
                            new ResultRegistryValue(value)
                    );
                    newCondition.result.setLoopsMessage(value);
                }
                else {
                    newCondition.result.executeSaveData(
                            stsBlockDescription.getSaveData(),
                            new ResultRegistryValue(stringBuilder.toString())
                    );
                    newCondition.result.setLoopsMessage(
                            stringBuilder.toString().length()
                    );
                }
                if (stsDescriptionRules.strictEnd) newConditions.clear();
                newConditions.add(newCondition);
            }
            else {
                stringBuilder.append(c);
                textIterator.next();
            }

            charNum++;
        }
        return newConditions;
    }
    private List<Condition> executeBlockCloseCode(StsBlockCloseCode stsBlockCloseCode){
        List<Condition> newConditions = new ArrayList<>();

        boolean inQuotation = false;
        int level = 0;
        StringBuilder stringBuilder = new StringBuilder();

        textIterator.previous();
        while (textIterator.hasNext()){
            char c = textIterator.next();


            if (stsBlockCloseCode.isQuotation() && c=='\'') inQuotation=!inQuotation;
            else if (stsBlockCloseCode.isDoubleQuotation() && c=='\"') inQuotation=!inQuotation;
            else if (!inQuotation || !stsBlockCloseCode.isSkipQuotationBraces()){
                if (stsBlockCloseCode.isCurlyBraces()){
                    if (c=='(') level++;
                    else if (c==')') level--;
                }
                if (stsBlockCloseCode.isRoundBrackets()){
                    if (c=='[') level++;
                    else if (c==']') level--;
                }
                if (stsBlockCloseCode.isSquareBrackets()){
                    if (c=='{') level++;
                    else if (c=='}') level--;
                }

                if (level==-1){
                    Result newResult = result.clone();
                    newResult.executeSaveData(
                            stsBlockCloseCode.getSaveData(),
                            new ResultRegistryValue(stringBuilder.toString())
                    );
                    newConditions.add(new Condition(nextStsBlock,textIterator,newResult));
                    break;
                }
            }
            stringBuilder.append(c);
            if (
                    stsBlockCloseCode.getSpecialSymbol()!=null &&
                    c==stsBlockCloseCode.getSpecialSymbol()
            ) stringBuilder.append(textIterator.next());

            if (level==0 && !stsBlockCloseCode.isStrictEnd()){
                Result newResult = result.clone();
                newResult.executeSaveData(
                        stsBlockCloseCode.getSaveData(),
                        new ResultRegistryValue(stringBuilder.toString())
                );
                newConditions.add(new Condition(nextStsBlock,textIterator,newResult));
            }
        }
        return newConditions;
    }
    private List<Condition> executeBlockFalseNumber(StsBlockFalseNumber stsBlockFalseNumber){
        result.executeSaveData(
                stsBlockFalseNumber.getSaveData(),
                new ResultRegistryValue( stsBlockFalseNumber.getValue() )
        );
        result.setLoopsMessage(stsBlockFalseNumber.getValue());
        return List.of(this);
    }
    private List<Condition> executeBlockNull(StsBlockNull stsBlockNull){
        result.executeSaveData(
                stsBlockNull.getSaveData(),
                new ResultRegistryValue()
        );
        result.setLoopsMessage(0);
        return List.of(new Condition(nextStsBlock,textIterator,result));
    }
    private List<Condition> executeBlockLink(StsBlockLink stsBlockLink){
        StsSaveData link = stsBlockLink.getLink();
        ResultRegistryValue value = result.getValue(link.getRegistry(),link.getNumber());

        if (value.getRegistryValueType() == ResultRegistryValueType.STRING){
            String blockText = value.getString();
            String readText = textIterator.readStringFromLength(blockText.length());
            if (blockText.equals(readText)){
                result.executeSaveData(stsBlockLink.getSaveData(),new ResultRegistryValue(readText));
            }
            else return new ArrayList<>();
        }
        else if (value.getRegistryValueType() == ResultRegistryValueType.INT){
            result.setLoopsMessage(value.getInt());
        }
        else if (value.getRegistryValueType() == ResultRegistryValueType.BOOLEAN){
            if (value.getBoolean()) result.setLoopsMessage(1);
            else result.setLoopsMessage(0);
        }

        return List.of(
                new Condition(nextStsBlock, textIterator, result)
        );
    }
    private List<Condition> executeBlockSystem(StsBlockSystem stsBlockSystem){
        if (stsBlockSystem.getType().equals("end")){
            if (textIterator.hasNext()){
                result.setSuccess(false);
                return new ArrayList<>();
            }
            else {
                return List.of(
                        new Condition(nextStsBlock, textIterator, result)
                );
            }
        }
        return new ArrayList<>();
    }

    public TextIterator getTextIterator() {return textIterator;}
    public Result getResult() {return result;}

    public boolean equals(Condition condition) {
        return this.textIterator.getText().equals(condition.textIterator.getText()) &&
                this.textIterator.getCharNumber() == condition.textIterator.getCharNumber() &&
                this.result.equals(condition.result) &&
                this.stsBlock == condition.stsBlock;
    }
}
