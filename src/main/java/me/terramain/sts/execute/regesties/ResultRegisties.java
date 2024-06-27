package terramain.sts.execute.regesties;

import me.terramain.textexecuter.StaticTextEditor;

import java.util.ArrayList;
import java.util.List;

public class ResultRegisties {
    private List<ResultRegistry> registries;

    public ResultRegisties(List<ResultRegistry> registries) {
        this.registries = registries;
    }
    public ResultRegisties() {
        this.registries = new ArrayList<>();
    }




    private int loadElement(int num){
        if (num>=registries.size()) {
            for (int i = registries.size(); i < num; i++) {
                registries.add(new ResultRegistry());
            }
        }
        return num;
    }
    private int loadElement(char c){
        if (c=='F' || c=='f'){//первый пустой реестр/ячейка //перед первым пустым реестром/ячейкой
            for (int i = 0; i < registries.size(); i++) {
                if (registries.get(i).getRegistryValues().size()==0){
                    if (c=='F' || i==0) return loadElement(i);
                    return loadElement(i-1);
                }
            }
        }
        else if (c=='E' || c=='e'){//последний не пустой реестр/ячейка //после последнего не пустого реестра/ячейки
            for (int i = registries.size()-1;i>=0;i--){
                if (registries.get(i).getRegistryValues().size()>0){
                    if (c=='E') return i;
                    return i+1;
                }
            }
        }
        return 0;
    }



    public List<ResultRegistry> getRegistries() {
        return registries;
    }

    public ResultRegisties clone(){
        List<ResultRegistry> registries = new ArrayList<>();
        this.registries.forEach(resultRegistry -> {
            registries.add(resultRegistry.clone());
        });
        return new ResultRegisties(new ArrayList<>(registries));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("registries:\n");
        for (int i = 0; i < registries.size(); i++) {
            stringBuilder.append(StaticTextEditor.addSpacesToLines(
                    i+": "+registries.get(i).toString(),
                    2
            ));
        }
        return stringBuilder.toString();
    }
}
