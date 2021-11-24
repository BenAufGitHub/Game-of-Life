import java.util.ArrayDeque;
import java.util.HashSet;

public class CellTracker {
    private HashSet<Cell> reviewCells = new HashSet<Cell>();
    private ArrayDeque<Cell> forChange = new ArrayDeque<Cell>();
    private Grid grid;
    private GridChangeListener listener;



    public CellTracker(Grid grid){
        this.grid = grid;
        listener = null;
    }


    public CellTracker(Grid grid, GridChangeListener listener){
        this.grid = grid;
        this.listener = listener;
    }


    /*
    puts cells to be changed into toBeChanged Queue, which will be used later to change Cells (called in Game class)
     */
    public void trackNextGridChanges(){
        HashSet<Cell> delete = new HashSet<>();

        for(Cell cell: reviewCells){
            int cellFriends = cell.getCompany();
            if(cell.toBeChanged(cellFriends))
                forChange.add(cell);
            if(!cell.isAlive() && cell.getCompany()==0)
                delete.add(cell);
        }
        for(Cell c : delete)
            reviewCells.remove(c);
    }


    public void loadNextGen(boolean colorAllTracked){
        for(Cell cell: forChange){
            changeCell(cell);
            visualizeChange(cell);

            if(cell.isAlive())
                track(cell.getNeighbours());
        }
        forChange.clear();

        if(colorAllTracked)
            allTrackedVisible();
    }


    public void visualizeChange(Cell cell){
        if(listener != null){
            if(cell.isAlive())
                listener.visualizeGridChange(cell.getX(), cell.getY(), "live!");
            else
                listener.visualizeGridChange(cell.getX(), cell.getY(), "die!");
        }
    }


    public void track(Cell... cells) {
        for(Cell cell : cells){
            reviewCells.add(cell);
        }
    }


    private boolean changeCell(Cell cell){
        if(cell.isAlive()){
            cell.setAlive(false);
            return false;
        }
        cell.setAlive(true);
        return true;
    }


    public void setGrid(Grid grid){
        this.grid = grid;
    }


    public Grid getGrid(){
        return grid;
    }


    /*
        method for testing purposes without GUI
     */
    public void allTrackedToTrue(){
        for(Cell c : reviewCells){
            c.setAlive(true);
        }
    }


    /*
        optional method for GUI
        gets invoked if showTracked == true (Settings)
        colors background of all tracked Cells
     */
    public void allTrackedVisible(){
        for(Cell cell : reviewCells){
            listener.visualizeGridChange(cell.getX(), cell.getY(), "color!");
        }
    }
}


