package components;

import graphics.SelectActionListener;

import java.util.ArrayDeque;
import java.util.HashSet;

public class CellTracker implements SelectActionListener {
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
    puts cells to be changed into toBeChanged Queue, which will be used later to change Cells (called in start.Game class)
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
        for(Cell cell : delete){
            reviewCells.remove(cell);
            listener.visualizeGridChange(cell.getX(), cell.getY(), Action.PLAIN);
        }
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
                listener.visualizeGridChange(cell.getX(), cell.getY(), Action.LIVE);
            else
                listener.visualizeGridChange(cell.getX(), cell.getY(), Action.DIE);
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
        method for testing purposes without graphics.GUI
     */
    public void allTrackedToTrue(){
        for(Cell c : reviewCells){
            c.setAlive(true);
        }
    }


    /*
        optional method for graphics.GUI
        gets invoked if showTracked == true (start.Settings)
        colors background of all tracked Cells
     */
    public void allTrackedVisible(){
        for(Cell cell : reviewCells){
            listener.visualizeGridChange(cell.getX(), cell.getY(), Action.COLOR);
        }
    }

    public void setListener(GridChangeListener listener){
        this.listener = listener;
    }


    @Override
    public void select(int x, int y) {
        Cell cell = getGrid().getCell(x, y);
        cell.setAlive(true);

        visualizeChange(cell);
        track(cell);
        track(cell.getNeighbours());
    }
}


