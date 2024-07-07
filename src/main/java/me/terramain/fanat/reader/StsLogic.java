package me.terramain.fanat.reader;

import me.terramain.fanat.Fanat;
import me.terramain.sts.Sts;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.textexecuter.TextHelper;

import java.util.List;

public class StsLogic {
    public static final String promts = Fanat.resources+"\\reader\\sts";
    public static final String defaultSts = TextHelper.readFile(promts+"\\default.sts");

    public static Result executeSts(String text, String stsName){
        String stsCode = defaultSts + TextHelper.readFile(promts+"\\"+stsName+".sts");
        List<Result> results = Sts.executeText(text,stsCode,true);
        if (results.size()==0) return null;
        return results.get(0);
    }


}
