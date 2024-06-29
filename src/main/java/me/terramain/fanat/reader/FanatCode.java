package me.terramain.fanat.reader;

import me.terramain.sts.execute.regesties.Result;

public class FanatCode {
    private String code;

    public FanatCode(String code) {
        this.code = code;
    }

    public FanatCode() {
        this.code="";
    }

    public void addCode(String code){
        this.code+=code;
    }

    public void execute(){
        Result result = StsLogic.executeSts(code,"code");
        result.foreach((value, registryNum, valueNum) -> {

        });
    }
}
