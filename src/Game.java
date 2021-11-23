import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

    private Grid grid;
    private CellTracker cellTracker;
    private int timeoutLength;

    public static void main(String args[]) throws InterruptedException {
        Grid grid = new Grid(5,5);
        Game game = new Game(grid);

        System.out.println(grid);
        game.run();
    }

    public Game(Grid grid){
        this.grid = grid;
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
     */
    public void act() {
        CellTracker ct = getCellTracker();
        ct.trackNextGridChanges();
        ct.loadNextGen();
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
