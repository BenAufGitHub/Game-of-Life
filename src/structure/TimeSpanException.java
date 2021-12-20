package structure;

public class TimeSpanException extends Exception{
    public TimeSpanException(int val, String problem){
        super("Your timespan " + val + " is " + problem + ". The timespan reaches from 30 to 12 000 Milliseconds");
    }
}
