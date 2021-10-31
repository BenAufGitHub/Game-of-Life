

public class Game {

    private Grid grid;
    private CellTracker cellTracker;

    public static void main(String args[]){
        Grid grid = new Grid(10,5);
        Game game = new Game(grid);
    }

    public Game(Grid grid){
        this.grid = grid;
        cellTracker = new CellTracker(grid);
    }



    /*
    one call equals one round
     */
    public void act(){

    }

    /*
    when stop button is clicked, await end of act and the pause the game:
    caution: GUI is presumably multi-threading
     */
    public void stop(){

    }
}
