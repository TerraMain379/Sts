package me.terramain.sts.execute.conditions;

import me.terramain.sts.StsBlocks;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.textexecuter.TextIterator;

import java.util.ArrayList;
import java.util.List;

public class ConditionsLine {
    protected StsBlocks stsBlocks;
    protected List<Condition> conditions;
    protected TextIterator textIterator;

    public ConditionsLine(StsBlocks stsBlocks, List<Condition> conditions, TextIterator textIterator) {
        this.stsBlocks = stsBlocks;
        this.conditions = conditions;
        this.textIterator = textIterator;
    }

    public ConditionsLine(StsBlocks stsBlocks, String text) {
        this.stsBlocks = stsBlocks;
        this.textIterator = new TextIterator(text);
        this.conditions = new ArrayList<>();
    }
    public ConditionsLine(StsBlocks stsBlocks, TextIterator textIterator) {
        this.stsBlocks = stsBlocks;
        this.textIterator = textIterator;
        this.conditions = new ArrayList<>();
    }

    public void execute(){
        execute(new Result());
    }
    public void execute(Result result){
        conditions.add(new Condition(stsBlocks.get(0),textIterator.clone(),result));
        boolean flag = true;
        int blockNumber = 0;
        while (flag){
            List<Condition> newConditions = new ArrayList<>();
            flag=false;
            for (Condition condition : conditions) {
                if (blockNumber < stsBlocks.size()-1) {
                    newConditions.addAll(condition.execute(stsBlocks.get(blockNumber + 1)));
                    flag = true;
                }
                else newConditions.addAll(condition.execute(null));
            }
            newConditions = newConditions.stream().filter(newCondition -> {
                return newCondition.getResult().isSuccess();
            }).toList();
            blockNumber++;

            conditions=new ArrayList<>();
            newConditions.forEach(newCondition -> {
                boolean flag2 = true;
                for (Condition condition : conditions) {
                    if (condition.equals(newCondition)) {
                        flag2 = false;
                        break;
                    }
                }
                if (flag2) conditions.add(newCondition);
            });
        }
    }

    public List<Condition> getConditions() {
        return conditions;
    }
    public TextIterator getTextIterator() {
        return textIterator;
    }
}
