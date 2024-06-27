package terramain.sts.exceptions;

public class StsException extends Exception {
    public StsException(String message) {
        super("STS: "+message);
    }

    public static void say(String massage){
        try {
            throw new StsException(massage);
        } catch (StsException e) {
            throw new RuntimeException(e);
        }
    }
}
