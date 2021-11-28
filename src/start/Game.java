package start;

import components.CellTracker;
import components.Grid;
import graphics.GUI;
import graphics.GUIRunnable;
import graphics.Selector;

import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

public class Game implements GUIRunnable {

    private Grid grid;
    private CellTracker cellTracker;
    private Settings settings;
    private int timeoutLength;
    private Boolean stopping = Boolean.FALSE;
    private Boolean noProcess = Boolean.TRUE;

    public static void main(String args[]) throws InterruptedException {
        Grid grid = new Grid(5,5);
        Settings settings = new Settings(true);
        Game game = new Game(grid, settings);

        GUI gui = game.generateGUI();
        game.getCellTracker().setListener(gui);
        new Selector(game.getCellTracker()).preselect(); //TODO
        System.out.println(grid); //TODO
    }

    public Game(Grid grid, Settings settings){
        this.grid = grid;
        this.settings = settings;
        this.timeoutLength = 1000;
        this.cellTracker = new CellTracker(grid);
    }


    /*
    returns new GUI
     */
    public GUI generateGUI(){
        Grid g = getGrid();
        Dimension dimension = new Dimension(g.getWidth(), g.getHeight());
        return new GUI(dimension, this);
    }


    /*
    main process, loops through act until a stop is requested
     */
    public void run() throws InterruptedException {
        if(ownProcessRequest() != true)
            return;

        while(!stopRequest()){
            act();
            TimeUnit.MILLISECONDS.sleep(getTimeoutLength());
            TimeUnit.MILLISECONDS.sleep(500); //TODO
        }
        setNoProcess(true);
    }


    /*
    one call equals one round
    first: ct looks for all cells to be changed, then he 'loads' the next gen
     */
    public synchronized void act() {
        boolean individuallyCalled = ownProcessRequest();

        CellTracker ct = getCellTracker();
        ct.trackNextGridChanges();
        ct.loadNextGen(settings.isTrackIndicated());

        System.out.println(grid); //TODO
        if(individuallyCalled)
            setNoProcess(true);
    }


    /*
    when stop button is clicked, await end of act and the pause the game:
    caution: graphics.GUI is presumably multi-threading
     */
    public void stop(){
        synchronized (stopping){
            stopping = true;
        }
    }


    @Override
    public boolean noProcess() {
        synchronized (noProcess){
            return noProcess;
        }
    }


    /*
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


    /*
    process only is accepted one at a time,
    when act is called, it checks wether it is its own process or invoked through the process in "run",
    return: boolean -> true: ownProcess means noProcess turns false; false: not accepted, in another process
     */
    private boolean ownProcessRequest(){
        synchronized (noProcess){
            if(noProcess){
                noProcess = false;
                return true;
            }
            return false;
        }
    }

    private void setNoProcess(Boolean bool) {
        synchronized (noProcess){
            noProcess = bool;
        }
    }

    public void setTimeoutLength(int milliseconds) throws TimeSpanException {
        if(milliseconds < 300)
            throw new TimeSpanException(milliseconds, "too small");
        if(milliseconds > 12000)
            throw new TimeSpanException(milliseconds, "too big");
        timeoutLength = milliseconds;
    }


    public CellTracker getCellTracker(){
        return cellTracker;
    }

    public Grid getGrid(){ return grid; }

    public int getTimeoutLength(){
        return timeoutLength;
    }

    public Settings getSettings(){ return settings; }
}
