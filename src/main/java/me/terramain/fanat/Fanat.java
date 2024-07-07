package me.terramain.fanat;


import me.terramain.fanat.reader.FanatReader;

public class Fanat {
    public static final String resources = "src\\main\\resources\\fanat";

    public static void main(String[] args) {
        FanatReader fanatReader = new FanatReader(resources);
        System.out.println(fanatReader.fanatProject.toString());
    }
}
