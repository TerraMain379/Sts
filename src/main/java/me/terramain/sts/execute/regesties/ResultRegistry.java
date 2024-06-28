package me.terramain.sts.execute.regesties;

import me.terramain.textexecuter.StaticTextEditor;

import java.util.ArrayList;
import java.util.List;

public class ResultRegistry {
    private List<ResultRegistryValue> registryValues;

    public ResultRegistry(List<ResultRegistryValue> registryValues) {
        this.registryValues = registryValues;
    }
    public ResultRegistry() {
        this(new ArrayList<>());
    }

    public ResultRegistryValue getElement(int num){
        return registryValues.get(loadElement(num));
    }
    public void setElement(int num, ResultRegistryValue value){
        registryValues.set(loadElement(num),value);
        for (int i = registryValues.size()-1; i >= 0; i--) {
            if (getElement(i).isNull()) registryValues.remove(i);
            else break;
        }
    }

    private int loadElement(int num){
        if (num==-1 || num==-2){//первый пустой реестр/ячейка //перед первым пустым реестром/ячейкой
            for (int i = 0; i < registryValues.size(); i++) {
                if (registryValues.get(i).isNull()){
                    if (num==-1 || i==0) return loadElement(i);
                    return loadElement(i-1);
                }
            }
            if (num==-1) return loadElement(registryValues.size());
            else return loadElement(registryValues.size()-1);
        }
        else if (num==-3 || num==-4){//последний не пустой реестр/ячейка //после последнего не пустого реестра/ячейки
            for (int i = registryValues.size()-1;i>=0;i--){
                if (registryValues.get(i).isNull()){
                    if (num==-3) return i;
                    return i+1;
                }
            }
            return loadElement(0);
        }

        if (num>=registryValues.size()) {
            for (int i = registryValues.size(); i <= num; i++) {
                registryValues.add(new ResultRegistryValue());
            }
        }
        return num;
    }

    public List<ResultRegistryValue> getRegistryValues() {
        return registryValues;
    }
    public int getSize(){
        return registryValues.size();
    }

    public ResultRegistry clone(){
        List<ResultRegistryValue> values = new ArrayList<>();
        this.registryValues.forEach(registryValue -> {
            values.add(registryValue.clone());
        });
        return new ResultRegistry(values);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("registry:\n");
        stringBuilder.append(StaticTextEditor.addSpacesToLines(
                "registryValues:\n",
                2
        ));
        for (int i = 0; i < registryValues.size(); i++) {
            stringBuilder.append(StaticTextEditor.addSpacesToLines(
                    i+": "+registryValues.get(i).toString(),
                    4
            ));
        }
        return stringBuilder.toString();
    }
}
