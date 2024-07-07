package me.terramain.sts.execute.regesties;

import me.terramain.sts.StsSaveData;
import me.terramain.textexecuter.StaticTextEditor;

public class Result {
    private ResultRegisties resultRegisties;
    private boolean isSuccess;
    private Integer loopsMessage;

    public Result(ResultRegisties resultRegisties, boolean isSuccess) {
        this.resultRegisties = resultRegisties;
        this.isSuccess = isSuccess;
        this.loopsMessage = null;
    }
    public Result() {
        this(new ResultRegisties(),true);
    }

    public ResultRegisties getResultRegisties() {return resultRegisties;}
    public void setResultRegisties(ResultRegisties resultRegisties) {this.resultRegisties = resultRegisties;}

    public ResultRegistryValue getValue(int registryNum, int valueNum){
        return resultRegisties.getValue(registryNum,valueNum);
    }
    public void setValue(int registryNum, int valueNum, ResultRegistryValue value){
        resultRegisties.setValue(registryNum,valueNum,value);
    }

    public boolean isSuccess() {return isSuccess;}
    public void setSuccess(boolean success) {isSuccess = success;}
    public Integer getLoopsMessage() {return loopsMessage;}
    public void setLoopsMessage(int loopsMessage) {this.loopsMessage = loopsMessage;}


    public void executeSaveData(StsSaveData stsSaveData, ResultRegistryValue value){
        if (stsSaveData==null) return;
        resultRegisties.setValue(
                stsSaveData.getRegistry(),
                stsSaveData.getNumber(),
                value
        );
    }

    public void foreach(IResultForeach iResultForeach){
        for (int i = 0; i < resultRegisties.getRegistries().size(); i++) {
            ResultRegistry resultRegistry = resultRegisties.getRegistries().get(i);
            for (int j = 0; j < resultRegistry.getRegistryValues().size(); j++) {
                ResultRegistryValue value = resultRegistry.getElement(j);
                if (!value.isNull()) {
                    iResultForeach.foreach(value, i, j);
                }
            }
        }
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

    public boolean equals(Result result) {
        try {
            for (int i = 0; i < resultRegisties.getRegistries().size(); i++) {
                ResultRegistry resultRegistry = resultRegisties.getRegistries().get(i);
                ResultRegistry resultRegistry2 = result.resultRegisties.getRegistries().get(i);
                for (int j = 0; j < resultRegistry.getRegistryValues().size(); j++) {
                    ResultRegistryValue resultRegistryValue = resultRegistry.getElement(j);
                    ResultRegistryValue resultRegistryValue2 = resultRegistry2.getElement(j);
                    if (!resultRegistryValue.equals(resultRegistryValue2)) return false;
                }
            }
            return true;
        } catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    public ResultRegistry getRegistry(int number) {
        return resultRegisties.getRegistries().get(number);
    }
}
