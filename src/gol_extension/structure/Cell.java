package gol_extension.structure;


/**
 * the Cell is a single, individual representation of life in Game of Life.
 * It stores important information about itself, such as position and is linked to its neighbours.
 */
public class Cell {

    private boolean alive = false;
    private boolean tracked = false;
    private Cell[] neighbours;
    private int x;
    private int y;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean isAlive(){
        return alive;
    }


    /**
     * reevaluates the neighbour-array
     * @return number of alive surrounding cells
     */
    public int getCompany(){
        int friends = 0;
        for(Cell c: neighbours){
            if(c.alive)
                friends++;
        }
        return friends;
    }

    /**
     * especially used by: Grid to initialize itself and the connections between the Cells
     * @param neighbours surrounding cells
     */
    public void setNeighbours(Cell[] neighbours){
        this.neighbours = neighbours;
    }

    public Cell[] getNeighbours() {
        return neighbours;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }


    /**
     * CAN EFFECT outcome of the game.
     * especially used by: Celltracker
     * @param bool
     */
    public void setTracked(boolean bool){
        tracked = bool;
    }

    public boolean getTracked(){
        return tracked;
    }
}
