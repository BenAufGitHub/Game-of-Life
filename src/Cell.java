public class Cell {
    private boolean alive;
    private Cell[] neighbours;
    private int x;
    private int y;

    public Cell(boolean alive, int x, int y){
        this.alive = alive;
        this.x = x;
        this.y = y;
    }

    public void setNeighbours(Cell[] neighbours){
        this.neighbours = neighbours;
    }

    public Cell[] getNeighbours(){
        return neighbours;
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean bool){
        alive = bool;
    }

    public int getCompany(){
        int souls = 0;
        for(int i=0; i<neighbours.length; i++){
            if(neighbours[i].isAlive())
                souls++;
        }
        return souls;
    }


    public boolean toBeChanged(int companions){
        if(alive){
            if(companions<2 || companions>3)
                return true;
        }
        return (companions==3 && !alive) ? true : false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
