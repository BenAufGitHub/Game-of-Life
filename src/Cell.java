public class Cell {

    private boolean alive = false;
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

    public int getCompany(){
        int friends = 0;
        for(Cell c: neighbours){
            if(c.alive)
                friends++;
        }
        return friends;
    }

    public void setNeighbours(Cell[] neighbours){
        this.neighbours = neighbours;
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
}
