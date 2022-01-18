package fourRow_extension;

public class Timer {
    private int maxTicks;
    private int ticks;
    private String toString;
    private String output = "X ";

    public Timer(int ticks){
        this.maxTicks = ticks;
        this.ticks = ticks;
        updateString(ticks);
    }


    private void updateString(int ticks) {
        toString = multiplySequence(output, ticks);
    }


    public void tick(){
        ticks--;
    }


    public int getRemainingTicks(){
        return ticks;
    }


    public void reset(){
        ticks = maxTicks;
    }


    public String remainingTimeToString(){
        updateString(ticks);
        return toString;
    }


    /**
     * @return amount < 0 will return null, amount == 0 will return an empty String literal
     */
    protected String multiplySequence(String sequence, int amount){
        if(amount < 0)
            return null;
        if(amount == 0)
            return "";
        if(amount == 1)
            return sequence;

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<amount; i++){
            sb.append(sequence);
        }
        return sb.toString();
    }
}
