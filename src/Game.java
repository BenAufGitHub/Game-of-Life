import java.util.concurrent.TimeUnit;

public abstract class Game {
    private Boolean stopping = Boolean.FALSE;
    private Boolean noProcess = Boolean.TRUE;
    private int timeoutLength = 1000;
    private Output output;


    public Game(Output op) {
        this.output = op;
        op.setGame(this);
    }


    public void run() throws InterruptedException {
        if(ownProcessRequest() != true)
            return;

        while(!stopRequest()){
            act();
            TimeUnit.MILLISECONDS.sleep(getTimeoutLength());
        }
        setRunning(false);
    }


    /**
     * game should be able to reset and start from new
     */
    public abstract void reset();


    /**
     * if you select a cell in the gui, this should be called to update the game grid
     */
    public abstract void select(int x, int y);


    /**
     * every method that can be called from the Gui has to thread safe, therefore, this checks
     * whether this is allowed to run
     * @return return true if accepted: changes noProcess to false
     */
    private final boolean ownProcessRequest(){
        synchronized (noProcess){
            if(!running()){
                setRunning(true);
                return true;
            }
            return false;
        }
    }


    /**
     * will be overridden by games, determines how the game is played
     */
    protected abstract void act();

    /**
     * callable from gui, wrapper for act method, which ensures a thread safe use
     */
    public final void requestAct(){
        if(ownProcessRequest() == false)
            return;
        act();
        setRunning(false);
    }


    /**
    when stop button is clicked, await end of act and the pause the game:
    caution: graphics.GUI is presumably multi-threading
     */
    public void stop(){
        synchronized (stopping){
            stopping = true;
        }
    }


    /**
    if stopping turns true, it is changed to false and true is returned,
    stops the running process
     */
    private boolean stopRequest(){
        synchronized (stopping){
            if(stopping){
                stopping = false;
                return true;
            }
            return false;
        }
    }


    private final void setRunning(Boolean bool) {
        synchronized (noProcess){
            noProcess = bool;
        }
    }

    /**
     * @return whether there is an ongoing process
     */
    public final boolean running(){
        synchronized(noProcess){
            return (noProcess) ? false : true;
        }
    }

    public void setTimeoutLength(int milliseconds) throws TimeSpanException {
        if(milliseconds < 300)
            throw new TimeSpanException(milliseconds, "too small");
        if(milliseconds > 12000)
            throw new TimeSpanException(milliseconds, "too big");
        timeoutLength = milliseconds;
    }

    public int getTimeoutLength(){ return timeoutLength; }

    public Output getOutput(){
        return output;
    }

}
