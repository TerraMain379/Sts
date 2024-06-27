package terramain.sts.execute.conditions;

import me.terramain.sts.StsBlocks;
import me.terramain.sts.execute.regesties.Result;
import me.terramain.sts.execute.regesties.ResultRegistryValue;
import me.terramain.sts.stsblocks.*;
import me.terramain.textexecuter.TextIterator;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class OldCondition {
    //result
    protected Result result;
    //blocks
    protected StsBlocks stsBlocks;
    protected int stsBlockNumber;
    //data
    protected String text;
    protected TextIterator textIterator;
    //analogical conditions
    protected List<OldCondition> analogicalConditions;

    public OldCondition(Result result, StsBlocks stsBlocks, int stsBlockNumber, TextIterator textIterator) {
        this.result = result;
        this.stsBlocks = stsBlocks;
        this.stsBlockNumber = stsBlockNumber;
        this.text = textIterator.getText();
        this.textIterator = textIterator;
        this.analogicalConditions = new ArrayList<>();
    }
    public OldCondition(Result result, StsBlocks stsBlocks, int stsBlockNumber, String text) {
        this.result = result;
        this.stsBlocks = stsBlocks;
        this.stsBlockNumber = stsBlockNumber;
        this.text = text;
        this.textIterator = new TextIterator(text);
        this.analogicalConditions = new ArrayList<>();
    }
    public OldCondition(StsBlocks stsBlocks, String text) {
        this.result = new Result();
        this.stsBlocks = stsBlocks;
        this.stsBlockNumber = 0;
        this.text = text;
        this.textIterator = new TextIterator(text);
        this.analogicalConditions = new ArrayList<>();
    }

    public List<OldCondition> execute(){
        for (StsBlock stsBlock:stsBlocks){
            if (stsBlock instanceof StsBlockText stsBlockText){
                executeStsBlockText(stsBlockText);
            }
            else if (stsBlock instanceof StsBlockStorage stsBlockStorage){
                if (stsBlockStorage instanceof StsBlockPossible stsBlockPossible){
                    //TODO
                }
                if (stsBlockStorage instanceof StsBlockOptions stsBlockOptions){
                    //TODO
                }
                if (stsBlockStorage instanceof StsBlockRepeat stsBlockRepeat){
                    //TODO
                }
                else {
                    executeStsBlockStorage(stsBlockStorage);
                }
            }
        }
        return analogicalConditions;
    }
    protected void executeStsBlockText(StsBlockText stsBlockText){
        String blockText = stsBlockText.getText();
        String resultText = textIterator.readStringFromLength(blockText.length());
        if (blockText.equals(resultText)){
            result.executeSaveData(stsBlockText.getSaveData(),new ResultRegistryValue(resultText));
        }
        else {
            result.setSuccess(false);
        }
    }
    private void executeStsBlockStorage(StsBlockStorage stsBlockStorage){
        int startNum = textIterator.getCharNumber();
        executeStsBlocks(stsBlockStorage.getStsBlocks());
        int endNum = textIterator.getCharNumber();
    }
    protected void executeStsBlocks(StsBlocks stsBlocks){
        OldCondition condition = new OldCondition(result,stsBlocks,0,textIterator);
        List<OldCondition> analogicalConditions = condition.execute();
        analogicalConditions.forEach(analogicalCondition -> {
            this.analogicalConditions.add(
                    new OldCondition(
                            analogicalCondition.result,
                            this.stsBlocks,
                            this.stsBlockNumber + 1,
                            analogicalCondition.textIterator
                    )
            );

        });
    }

    public Result getResult() {
        return result;
    }
}
