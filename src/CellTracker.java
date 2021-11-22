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
        HashSet<Cell> temporary = new HashSet<>();
        for(Cell cell: reviewCells){
            int cellFriends = cell.getCompany();
            if(cell.toBeChanged(cellFriends))
                forChange.add(cell);
            temporary.add(cell);
        }
        for(Cell c : temporary){
            track(c.getNeighbours());
        }
    }

    public void loadNextGen(){
        for(Cell cell: forChange){
            changeCell(cell);
            visualizeChange(cell);
        }
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

    public void allTrackedToTrue(){
        for(Cell c : reviewCells){
            c.setAlive(true);
        }
    }
}


