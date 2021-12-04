package components;

import graphics.SelectActionListener;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

public class CellTracker implements SelectActionListener {
    private HashSet<Cell> reviewCells = new HashSet();
    private ArrayDeque<Cell> forChange = new ArrayDeque();
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
    puts cells to be changed into toBeChanged Queue, which will be used later to change Cells (called in start.structure.Game class)
    all from untrackCell-Set can be removed from
     */
    public void trackNextGridChanges(){
        for(Cell cell: getReviewCells()){
            int cellFriends = cell.getCompany();
            if(cell.toBeChanged(cellFriends))
                forChange.add(cell);
        }
    }


    /*
    this is were the process of going from one generation to the next is managed:
    -changes all cells that were selected out of reviewed cells
    -now deletes dead Cells from review that are not next to a living cell
     */
    public void loadNextGen(){
        for(Cell cell: forChange){
            changeCell(cell);
            visualizeChange(cell);

            if(cell.isAlive())
                track(cell.getNeighbours());
        }
        forChange.clear();

        cleanReviewList();
        if(listener.colouringRequest() == true)
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
            getReviewCells().add(cell);
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


    /*
        method for testing purposes without graphics.structure.GUI
     */
    public void allTrackedToTrue(){
        for(Cell c : getReviewCells()){
            c.setAlive(true);
        }
    }


    /*
        optional method for graphics.structure.GUI
        gets invoked if showTracked == true (structure.Settings)
        colors background of all tracked Cells
     */
    public void allTrackedVisible(){
        for(Cell cell : getReviewCells()){
            listener.visualizeGridChange(cell.getX(), cell.getY(), Action.COLOR);
        }
    }


    /*
    if the reviewed cell is dead and has no company, it will no longer be tracked,
    only those will remain that are alive or next to an alive Cell
     */
    public void cleanReviewList(){
        ArrayList<Cell> clear = new ArrayList<>();
        for(Cell cell : getReviewCells()){
            if(!cell.isAlive() && cell.getCompany() == 0)
                clear.add(cell);
        }
        for(Cell cell : clear){
            reviewCells.remove(cell);
            if(listener.colouringRequest() == true )
                listener.visualizeGridChange(cell.getX(), cell.getY(), Action.PLAIN);
        }
    }


    @Override
    public void select(int x, int y) {
        Cell cell = getGrid().getCell(x, y);
        cell.setAlive(true);

        visualizeChange(cell);
        track(cell);
        track(cell.getNeighbours());

        colorGroup(cell);
    }


    /*
    Colors Cell and its neighbours red
     */
    public void colorGroup(Cell cell){
        listener.visualizeGridChange(cell.getX(), cell.getY(),Action.COLOR);
        for(Cell c : cell.getNeighbours())
            listener.visualizeGridChange(c.getX(),c.getY(), Action.COLOR);
    }

    public void setListener(GridChangeListener listener){
        this.listener = listener;
    }

    public HashSet<Cell> getReviewCells(){
        return reviewCells;
    }

    public void setGrid(Grid grid){
        this.grid = grid;
    }

    public Grid getGrid(){
        return grid;
    }
}


