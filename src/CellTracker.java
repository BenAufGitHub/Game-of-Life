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
        for(Cell cell: reviewCells){
            int cellFriends = cell.getCompany();
            if(cell.toBeChanged(cellFriends))
                forChange.add(cell);
            track(cell.getNeighbours());
        }
    }

    public void loadNextGen(){
        for(Cell cell: forChange){
            changeCell(cell);
            if(listener != null){
                if(cell.isAlive())
                    listener.visualizeGridChange(cell.getX(), cell.getY(), "live!");
                else
                    listener.visualizeGridChange(cell.getX(), cell.getY(), "die!");
            }
        }
    }

    private void track(Cell... cells) {
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
}
