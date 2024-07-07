package me.terramain.fanat;

public class FanatException extends Exception {
    public FanatException(String message) {
        super("FANAT: "+message);
    }

    public static void say(String massage){
        try {
            throw new FanatException(massage);
        } catch (FanatException e) {
            throw new RuntimeException(e);
        }
    }
}
