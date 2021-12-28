package structure;

public class TimeSpanException extends Exception{
    public TimeSpanException(int val, String problem){
        super("Your time span " + val + " is " + problem + ". The time span reaches from 30 to 12 000 Milliseconds");
    }
}
