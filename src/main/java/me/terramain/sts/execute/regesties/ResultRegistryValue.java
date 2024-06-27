package me.terramain.sts.execute.regesties;

public class ResultRegistryValue {
    private Object object;
    private ResultRegistryValueType registryValueType;

    public ResultRegistryValue(Object object, ResultRegistryValueType registryValueType) {
        this.object = object;
        this.registryValueType = registryValueType;
    }
    public ResultRegistryValue(String text) {
        this(text, ResultRegistryValueType.STRING);
    }
    public ResultRegistryValue(int num) {
        this(num, ResultRegistryValueType.INT);
    }
    public ResultRegistryValue(boolean bool) {
        this(bool, ResultRegistryValueType.BOOLEAN);
    }
    public ResultRegistryValue() {
        this(null, ResultRegistryValueType.NULL);
    }

    public String getString(){
        return (String) object;
    }
    public int getInt(){
        return (int) object;
    }
    public boolean getBoolean(){
        return (boolean) object;
    }

    public void setString(String str){
        this.object = str;
        this.registryValueType = ResultRegistryValueType.STRING;
    }
    public void setInt(int num){
        this.object = num;
        this.registryValueType = ResultRegistryValueType.INT;
    }
    public void setBoolean(boolean bool){
        this.object = bool;
        this.registryValueType = ResultRegistryValueType.BOOLEAN;
    }
    public void setNull(){
        this.object = null;
        this.registryValueType = ResultRegistryValueType.NULL;
    }

    public ResultRegistryValueType getRegistryValueType() {
        return registryValueType;
    }

    public boolean isNull(){
        return registryValueType==ResultRegistryValueType.NULL;
    }


    public ResultRegistryValue clone(){
        if (registryValueType==ResultRegistryValueType.STRING)
            return new ResultRegistryValue(getString());
        if (registryValueType==ResultRegistryValueType.INT)
            return new ResultRegistryValue(getInt());
        if (registryValueType==ResultRegistryValueType.BOOLEAN)
            return new ResultRegistryValue(getBoolean());
        return new ResultRegistryValue();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (registryValueType==ResultRegistryValueType.STRING)
            stringBuilder.append("\"").append(getString()).append("\"-STRING\n");
        if (registryValueType==ResultRegistryValueType.INT)
            stringBuilder.append(getInt()).append("-INT\n");
        if (registryValueType==ResultRegistryValueType.BOOLEAN)
            stringBuilder.append(getBoolean()).append("-BOOLEAN\n");
        return stringBuilder.toString();
    }
}
