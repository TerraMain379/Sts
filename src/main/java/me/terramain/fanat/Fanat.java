package me.terramain.fanat;

public class Fanat {
    public static final String RESOURCES = "src\\main\\resources\\fanat\\";
    public static final String STS_EXAMPLES = RESOURCES+"sts\\";

    public static void main(String[] args) {
        System.out.println(
                FanatReader.executeStsRules(
                        RESOURCES+"projects\\Main.fan",
                        "fanat-file.sts"
                )
        );
    }
}
