package terramain.sts.execute.regesties;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.StaticTextEditor;

public class Result {
    private ResultRegisties resultRegisties;
    private boolean isSuccess;

    public Result(ResultRegisties resultRegisties, boolean isSuccess) {
        this.resultRegisties = resultRegisties;
        this.isSuccess = isSuccess;
    }
    public Result() {
        this(new ResultRegisties(),true);
    }

    public ResultRegisties getResultRegisties() {return resultRegisties;}
    public void setResultRegisties(ResultRegisties resultRegisties) {this.resultRegisties = resultRegisties;}
    public boolean isSuccess() {return isSuccess;}
    public void setSuccess(boolean success) {isSuccess = success;}

    public void executeSaveData(StsSaveData stsSaveData, ResultRegistryValue value){
        if (stsSaveData==null) return;
        resultRegisties.setValue(stsSaveData.getRegistry(),stsSaveData.getNumber(),value);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("result:\n");
        stringBuilder.append(StaticTextEditor.addSpacesToLines(
                "isSuccess: "+isSuccess+"\n",
                2
        ));
        stringBuilder.append(StaticTextEditor.addSpacesToLines(
                resultRegisties.toString(),
                2
        ));
        return stringBuilder.toString();
    }


    public Result clone(){
        return new Result(resultRegisties.clone(),isSuccess);
    }
}
