import structure.*;

import java.awt.Color;
import java.util.HashMap;

public class GameOfLife extends Game {
    private final static Blueprint live = new Blueprint(Color.WHITE, null);
    private final static Blueprint clear = new Blueprint(Color.GRAY, null);

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
        getCellTracker().setGrid(new Grid(getOutput().gridWidth(), getOutput().gridHeight()));
        getOutput().clear();
    }


    /**
     * this will be called if the output is clicked at specific coordinates from grid, so that the game can react to that
     * @param x
     * @param y
     */
    @Override
    public void clicked(int x, int y) {
        getCellTracker().clicked(x,y);
        HashMap<Cell, CellTracker.Update> log = getCellTracker().getUpdateLog();
        for(Cell cell : log.keySet()) {
            CellTracker.Update upd = log.get(cell);
            switch(upd){
                case DELETE,DIE -> getOutput().showAction(cell.getX(),cell.getY(), clear);
                case LIVE -> getOutput().showAction(cell.getX(),cell.getY(), live);
            }
        }
    }


    /**
    lets Celltracker update grid and log the changes, then outputs it to the Output
     TODO update logging into a queue
     */
    @Override
    protected void act() {
        getCellTracker().act();
        HashMap<Cell, CellTracker.Update> log = getCellTracker().getUpdateLog();
        for(Cell cell : log.keySet()) {
            CellTracker.Update upd = log.get(cell);
            switch(upd){
                case DELETE, DIE -> getOutput().showAction(cell.getX(),cell.getY(), clear);
                case LIVE -> getOutput().showAction(cell.getX(),cell.getY(), live);
            }
        }
        if(log.isEmpty())
            stopRun();
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