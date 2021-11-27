package start;

public class TimeSpanException extends Exception{
    public TimeSpanException(int val, String problem){
        super("Your timespan " + val + " is " + problem + ". The timespan reaches from 300 to 12 000 Milliseconds");
    }
}
