import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

    private Grid grid;
    private CellTracker cellTracker;
    private Settings settings;
    private int timeoutLength;

    public static void main(String args[]) throws InterruptedException, IOException {
        Grid grid = new Grid(5,5);
        Settings settings = new Settings(true);
        Game game = new Game(grid, settings);

        GUI frame = new GUI();
        System.out.println(grid);
        game.run();
    }

    public Game(Grid grid, Settings settings){
        this.grid = grid;
        this.settings = settings;
        cellTracker = new CellTracker(grid);
        new Selector(cellTracker).preselect();
        timeoutLength = 1000;
    }


    public CellTracker getCellTracker(){
        return cellTracker;
    }


    public void run() throws InterruptedException {
        Scanner scan = new Scanner(System.in);

        while(!scan.next().equals("q")){
            act();
            System.out.println(grid); //TODO temporary
            TimeUnit.MILLISECONDS.sleep(getTimeoutLength());

        }
    }

    /*
    one call equals one round
    first: ct looks for all cells to be changed, then he 'loads' the next gen
     */
    public void act() {
        CellTracker ct = getCellTracker();
        ct.trackNextGridChanges();
        ct.loadNextGen(settings.isTrackIndicated());
    }

    /*
    when stop button is clicked, await end of act and the pause the game:
    caution: GUI is presumably multi-threading
     */
    public void stop(){

    }


    public void setTimeoutLength(int milliseconds) throws TimeSpanException{
        if(milliseconds < 300)
            throw new TimeSpanException(milliseconds, "too small");
        if(milliseconds > 12000)
            throw new TimeSpanException(milliseconds, "too big");
        timeoutLength = milliseconds;
    }

    public int getTimeoutLength(){
        return timeoutLength;
    }

    public Settings getSettings(){ return settings; }
}
