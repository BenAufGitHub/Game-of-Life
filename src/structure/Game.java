package structure;

import java.util.concurrent.TimeUnit;

public abstract class Game {
    private final Object stopLock = new Object();
    private final Object processCheckLock = new Object();

    private boolean noProcess = true;
    private boolean stopping = false;
    private boolean forceInterrupt = false;
    private int timeoutLength = 800;
    private Output output;


    public Game(Output op) {
        this.output = op;
        op.setGame(this);
    }


    /**
     * game should be able to reset and start from new
     */
    public abstract void reset();


    //------------------------------------------ running ---------------------------------------------


    public final void run() throws InterruptedException {
        if(ownProcessRequest() != true)
            return;

        stopping = false;
        forceInterrupt = false;
        while(!stopRequest() && !forceInterrupt){
            act();
            TimeUnit.MILLISECONDS.sleep(getTimeoutLength());
        }
        forceInterrupt = false;
        setRunning(false);
    }


    /**
     * every method that can be called from the Gui has to thread safe, therefore, this checks
     * whether this is allowed to run
     * @return return true if accepted: changes noProcess to false
     */
    private boolean ownProcessRequest(){
        synchronized (processCheckLock){
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
    caution: graphics.structure.GUI is presumably multi-threading
     */
    public void stop(){
        synchronized (stopLock){
            stopping = true;
        }
    }


    /**
    if stopping turns true, it is changed to false and true is returned,
    stops the running process
     */
    private boolean stopRequest(){
        synchronized (stopLock){
            if(stopping){
                stopping = false;
                return true;
            }
            return false;
        }
    }


    private void setRunning(Boolean bool) {
        synchronized (processCheckLock){
            noProcess = !bool;
        }
    }

    /**
     * @return whether there is an ongoing process
     */
    public final boolean running(){
        synchronized(processCheckLock){
            return !noProcess;
        }
    }


    /**
     * sub-classes can call this method if they want to interrupt the running-process on the next act
     */
    protected void stopRun(){
        forceInterrupt = true;
    }


    //------------------------------- clicking and hovering --------------------------------------------

    /**
     * determines what happens when a cell is clicked at the output Object (Output has to be setup manually).
     */
    public synchronized final void clicked(int x, int y){
        onClick(x,y);
    }

    /** Override to get functionality, is called by "clicked" method. */
    protected void onClick(int x, int y){}

    /**
     * determines what happens if a cell is hovered in the output object (Output has be set up manually).
     */
    public synchronized final void hovered(int x, int y){
        onHover(x, y);
    }

    /** Override to get functionality, is called by "hovered" method. */
    protected void onHover(int x, int y){}


    // ----------------------------------------- other --------------------------------------------------


    public void setTimeoutLength(int milliseconds) throws TimeSpanException {
        if(milliseconds < 0)
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
