package me.terramain.fanat;

import me.terramain.sts.Sts;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.textexecuter.TextHelper;

import java.util.List;

public class FanatReader {
    public static final String DEFAULT_STS_CODE = TextHelper.readFile(Fanat.RESOURCES+"sts\\default-code.sts");
    public static Result executeStsRules(String text, String stsFile){
        String stsCode = TextHelper.readFile(Fanat.STS_EXAMPLES + stsFile) + DEFAULT_STS_CODE;
        List<Result> results = Sts.executeText(text,stsCode,true);
        if (results.size()==0) return null;
        return results.get(0);
    }
}
