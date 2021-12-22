import structure.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;

public class GameOfLife extends Game {

    CellTracker cellTracker;


    /**
     * the coordinates need to be in bounds of output grid, else the errorhandler takes over
     * @param op -> output
     * @param x
     * @param y
     */
    public GameOfLife(Output op, int x, int y) {
        super(op);
        if(x > op.gridWidth() || y > op.gridHeight()){
            if( op instanceof GUI){
                new Thread( () -> {
                    ErrorHandler.catchError((GUI) op, new IndexOutOfBoundsException("Dimensions "+x+" and "+y+" out of Output bounds "+ op.gridWidth()+" and "+op.gridHeight()+"!"), 1);
                }).start();
            }
            throw new IndexOutOfBoundsException("Dimensions "+x+" and "+y+" out of Output bounds "+ op.gridWidth()+" and "+op.gridHeight()+"!");
        }
        this.cellTracker = new CellTracker(new Grid(x,y));
    }


    /**
     * TODO not in use, comes with addition of reset button
     */
    @Override
    public void reset() {
        int x = getCellTracker().getGrid().getWidth();
        int y = getCellTracker().getGrid().getHeight();
        getCellTracker().setGrid(new Grid(x,y));
        getOutput().clear();
        getCellTracker().getUpdateLog().clear();
    }


    /**
     * this will be called if the output is clicked at specific coordinates from grid, so that the game can react to that
     * @param x
     * @param y
     */
    @Override
    public void clicked(int x, int y) {
        //if running or exceeding x or y bounds -> return
        if(running() || x >= getCellTracker().getGrid().getWidth() || y >= getCellTracker().getGrid().getHeight())
            return;
        getCellTracker().clicked(x,y);
        while(!getCellTracker().getUpdateLog().isEmpty())
            updateGUI(getCellTracker().getUpdateLog().pop());
    }


    /**
    lets Celltracker update grid and log the changes, then outputs it to the Output
     */
    @Override
    protected void act() {
        getCellTracker().act();
        if(getCellTracker().getUpdateLog().peekFirst().isEmpty())
            stopRun();
        while(!getCellTracker().getUpdateLog().isEmpty())
            updateGUI(getCellTracker().getUpdateLog().pop());
    }


    /**
     * updates GUI with the given Update
     * @param updates
     */
    public void updateGUI(HashMap<Cell, CellTracker.Update> updates){
        for(Cell cell : updates.keySet()) {
            //TODO updateCell(cell, updates.get(cell));
        }
        getOutput().refresh();
    }


    public CellTracker getCellTracker(){
        return cellTracker;
    }


    /**
     * called with care,  since the celltracker tracks the files cells
     * @param cellTracker
     */
    public void setCellTracker(CellTracker cellTracker){
        this.cellTracker = cellTracker;
    }
}