package start;

import components.CellTracker;
import components.Grid;
import graphics.GUI;
import graphics.GUIRunnable;
import graphics.Selector;

import java.awt.Dimension;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game implements GUIRunnable {

    private Grid grid;
    private CellTracker cellTracker;
    private Settings settings;
    private int timeoutLength;
    private Boolean isStopped = Boolean.FALSE;

    public static void main(String args[]) throws InterruptedException {
        Grid grid = new Grid(5,5);
        Settings settings = new Settings(true);
        Game game = new Game(grid, settings);

        game.generateGUI();
        System.out.println(grid); //TODO
        game.run();
    }

    public Game(Grid grid, Settings settings){
        this.grid = grid;
        this.settings = settings;
        cellTracker = new CellTracker(grid);
        new Selector(cellTracker).preselect();
        timeoutLength = 1000;
    }

    public void generateGUI(){
        Grid g = getGrid();
        Dimension dimension = new Dimension(g.getWidth(), g.getHeight());
        new GUI(dimension, this);
    }

    public void run() throws InterruptedException {
        while(true){
            synchronized (isStopped){
                if(isStopped){
                    isStopped = false;
                    return;
                }
            }
            act();
            TimeUnit.MILLISECONDS.sleep(getTimeoutLength());
        }
    }

    /*
    one call equals one round
    first: ct looks for all cells to be changed, then he 'loads' the next gen
     */
    public synchronized void act() {
        CellTracker ct = getCellTracker();
        ct.trackNextGridChanges();
        ct.loadNextGen(settings.isTrackIndicated());
    }

    /*
    when stop button is clicked, await end of act and the pause the game:
    caution: graphics.GUI is presumably multi-threading
     */
    public void stop(){

    }

    @Override
    public void forceStop() {

    }

    @Override
    public boolean noProcess() {
        if(isStopped == true)
            return false;
        return true;
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
