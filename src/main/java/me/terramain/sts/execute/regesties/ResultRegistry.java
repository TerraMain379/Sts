package terramain.sts.execute.regesties;

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
    }
    public ResultRegistryValue getElement(char c){
        return registryValues.get(loadElement(c));
    }
    public void setElement(char c, ResultRegistryValue value){
        registryValues.set(loadElement(c),value);
    }
    public int loadElement(String path){
        try {
            return Integer.valueOf(path);
        } catch (NumberFormatException e){

        }
    }
    private int loadElement(int num){
        if (num>=registryValues.size()) {
            for (int i = registryValues.size(); i < num; i++) {
                registryValues.add(new ResultRegistryValue());
            }
        }
        return num;
    }
    private int loadElement(char c){
        if (c=='F' || c=='f'){//первый пустой реестр/ячейка //перед первым пустым реестром/ячейкой
            for (int i = 0; i < registryValues.size(); i++) {
                if (registryValues.get(i).getRegistryValueType()==ResultRegistryValueType.NULL){
                    if (c=='F' || i==0) return loadElement(i);
                    return loadElement(i-1);
                }
            }
        }
        else if (c=='E' || c=='e'){//последний не пустой реестр/ячейка //после последнего не пустого реестра/ячейки
            for (int i = registryValues.size()-1;i>=0;i--){
                if (registryValues.get(i).getRegistryValueType()!=ResultRegistryValueType.NULL){
                    if (c=='E') return i;
                    return i+1;
                }
            }
        }
        return 0;
    }

    public List<ResultRegistryValue> getRegistryValues() {
        return registryValues;
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
