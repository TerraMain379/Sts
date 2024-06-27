package me.terramain.sts;

import me.terramain.sts.exceptions.StsException;
import me.terramain.sts.execute.conditions.Condition;
import me.terramain.sts.execute.conditions.ConditionsLine;
import me.terramain.sts.execute.regesties.Result;

import java.util.ArrayList;
import java.util.List;

public class Sts {
    public static char getSystemChar(char c){
        if (c=='\\') return '\\';
        if (c=='"') return '"';
        if (c=='n') return '\n';
        if (c=='f') return '\f';
        if (c=='t') return '\t';
        if (c=='r') return '\r';
        if (c=='b') return '\b';
        return 0;
    }

    public static List<Result> executeText(String text, String stsCode){
        StsReader stsReader = new StsReader(stsCode);
        ConditionsLine conditionsLine = new ConditionsLine(stsReader.getStsBlocks(),text);
        conditionsLine.execute();
        List<Condition> conditions = conditionsLine.getConditions();
        List<Result> results = new ArrayList<>();
        conditions.forEach(condition -> results.add(condition.getResult()));
        return results;
    }

    public static void exception(String massage){
        try {
            throw new StsException(massage);
        } catch (StsException e) {throw new RuntimeException(e);}
    }
}
