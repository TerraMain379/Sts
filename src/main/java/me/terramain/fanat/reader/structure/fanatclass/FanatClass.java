package me.terramain.fanat.reader.structure.fanatclass;

import me.terramain.fanat.FanatException;
import me.terramain.fanat.reader.StsLogic;
import me.terramain.fanat.reader.structure.FanatCode;
import me.terramain.fanat.reader.structure.FanatFunction;
import me.terramain.fanat.reader.structure.FanatSecLevel;
import me.terramain.fanat.reader.structure.FanatVariable;
import me.terramain.fanat.reader.structure.link.FanatLink;
import me.terramain.fanat.reader.structure.link.FanatLinkType;
import me.terramain.sts.execute.regesties.Result;

import java.util.ArrayList;
import java.util.List;

public class FanatClass {
    public static final FanatClassType classType = FanatClassType.CLASS;

    public String name;
    public FanatLink classPackage;
    public FanatLink parent;
    public FanatLink implemented;
    public List<FanatLink> imports;

    public List<FanatVariable> fanatVariables;
    public List<FanatFunction> fanatFunctions;

    public static FanatClass generate(String name, FanatLink parent, FanatLink implemented, String code, FanatClassType fanatClassType){
        if (fanatClassType==FanatClassType.CLASS) return new FanatClass(name,parent,implemented,code);
        if (fanatClassType==FanatClassType.ENUM) return new FanatEnum(name,parent,implemented,code);

        FanatException.say("not supported class type: " + fanatClassType);
        return null;
    }
    public FanatClass(String name, FanatLink parent, FanatLink implemented, String code) {
        this.name = name;
        this.parent = parent;
        this.implemented = implemented;
        read(code);
    }

    private void read(String code){
        Result classReadResult = StsLogic.executeSts(code,"class");
        classReadResult.getResultRegisties().foreach(resultRegistry -> {
            int objectType = resultRegistry.getElement(0).getInt();
            if (objectType==0){
                classPackage = new FanatLink(
                        resultRegistry.getElement(1).getString(),
                        FanatLinkType.PACKAGE
                );
            }
            else if (objectType==1){
                imports.add(new FanatLink(
                        resultRegistry.getElement(1).getString(),
                        FanatLinkType.CLASS
                ));
            }
            else if (objectType==2 || objectType==3){
                String name = resultRegistry.getElement(1).getString();
                FanatLink type = new FanatLink(
                        resultRegistry.getElement(4).getString(),
                        FanatLinkType.CLASS
                );
                FanatSecLevel secLevel = FanatSecLevel.valueOf(resultRegistry.getElement(2).getInt());
                boolean isStatic = resultRegistry.getElement(3).getBoolean();
                if (objectType==2){
                    fanatVariables.add(new FanatVariable(
                            name,type,secLevel,isStatic
                    ));
                }
                else {
                    List<FanatLink> params = new ArrayList<>();
                    String args = resultRegistry.getElement(5).getString().strip();
                    if (!args.equals("")) {
                        Result argsReadResult = StsLogic.executeSts(
                                args,
                                "fun_args"
                        );
                        argsReadResult.getRegistry(0).foreach((value, valueNum) ->
                            params.add(new FanatLink(value.getString(),FanatLinkType.CLASS))
                        );
                    }

                    FanatFunction fanatFunction = new FanatFunction(
                            name,secLevel,isStatic,type,params,
                            new FanatCode(resultRegistry.getElement(6).getString())
                    );
                    fanatFunctions.add(fanatFunction);
                }
            }
        });
    }

    @Override
    public String toString() {
        return "FanatClass: \n" + "  name: " + name + "\n" +
                "  parent: " + parent + "\n" +
                "  implemented: " + implemented + "\n";
    }
}
