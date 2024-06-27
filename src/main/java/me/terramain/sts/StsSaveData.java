package me.terramain.sts;

public class StsSaveData {
    private int registry;
    private int number;

    public StsSaveData(int registry, int number) {
        this.registry = registry;
        this.number = number;
    }

    public int getRegistry() {return registry;}
    public int getNumber() {return number;}
    public void setNumber(int number) {this.number = number;}
    public void setRegistry(int registry) {this.registry = registry;}


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("saveData:\n");
        stringBuilder.append("  ").append("registry:").append(registry).append('\n');
        stringBuilder.append("  ").append("number:").append(number).append('\n');
        return stringBuilder.toString();
    }

    public static int getPathFromChar(char c){
        if (c=='F') return -1;
        if (c=='f') return -2;
        if (c=='E') return -3;
        if (c=='e') return -4;
        if (c=='S') return -5;
        return 0;
    }
}
