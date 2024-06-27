package terramain.sts;

public class StsSaveData {
    private int registry;
    private int number;// -1 = auto

    public StsSaveData(int registry, int number) {
        this.registry = registry;
        this.number = number;
    }

    public StsSaveData(int registry) {
        this.registry = registry;
        this.number = -1;
    }

    public int getRegistry() {return registry;}
    public int getNumber() {return number;}

    public void setRegistry(int registry) {this.registry = registry;}
    public void setNumber(int number) {this.number = number;}

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("saveData:\n");
        stringBuilder.append("  ").append("registry:").append(registry).append('\n');
        stringBuilder.append("  ").append("number:").append(number).append('\n');
        return stringBuilder.toString();
    }
}
