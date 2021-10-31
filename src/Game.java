public class Game {

    private Grid grid;

    public static void main(String args[]){
        Grid grid = new Grid(10,5);
        System.out.println(grid);
    }

    public Game(Grid grid){
        this.grid = grid;
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
