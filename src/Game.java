import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

public class Game {

    private Grid grid;
    private CellTracker cellTracker;
    private int timeoutLength;

    public static void main(String args[]) throws InterruptedException {
        Grid grid = new Grid(10,5);
        Game game = new Game(grid);

        System.out.println(grid);
        game.act();
    }

    public Game(Grid grid){
        this.grid = grid;
        cellTracker = new CellTracker(grid);
        timeoutLength = 1000;
    }


    public CellTracker getCellTracker(){
        return cellTracker;
    }

    /*
    one call equals one round
     */
    public void act() throws InterruptedException {
        CellTracker ct = getCellTracker();
        ct.trackNextGridChanges();
        ct.loadNextGen();

        System.out.println(grid); //TODO temporary
        TimeUnit.MILLISECONDS.sleep(getTimeoutLength());
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
}
