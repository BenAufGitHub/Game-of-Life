package gol_extension;

import gol_extension.updates.Updater;
import gol_extension.updates.UpdaterClean;
import gol_extension.updates.Updates;
import structure.ErrorHandler;
import structure.GUI;
import structure.Game;
import structure.Output;

import java.util.HashMap;

public class GameOfLife extends Game {

    private CellTracker cellTracker;
    private Updater updater;

    /**
     * the coordinates need to be in bounds of output grid, else the Error-Handler takes over
     * @param op -> output
     * @param x
     * @param y
     */
    public GameOfLife(Output op, int x, int y) {
        super(op);
        validateBounds(x, y);
        this.cellTracker = new CellTracker(new Grid(x,y));
        this.updater = new UpdaterClean(getOutput());
    }


    /**
     * called to check whether x and y are in output bounds.
     * if not && GUI: process cancel (ErrorHandler).
     * if not && !GUI: throws IndexOutOfBoundsException at Runtime.
     * @param x
     * @param y
     */
    private void validateBounds(int x, int y){
        final Output op = getOutput();
        if(x > op.gridWidth() || y > op.gridHeight()){
            if( op instanceof GUI){
                new Thread( () -> {
                    Exception exc = new IndexOutOfBoundsException("Dimensions "+x+" and "+y+" out of Output bounds "+ op.gridWidth()+" and "+ op.gridHeight()+"!");
                    ErrorHandler.catchError((GUI) op, exc, 1);
                }).start();
            }
            throw new IndexOutOfBoundsException("Dimensions "+x+" and "+y+" out of Output bounds "+ op.gridWidth()+" and "+op.gridHeight()+"!");
        }
    }


    /**
     * Needs to be called at Clear-Button click.
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
    public void updateGUI(HashMap<Cell, Updates> updates){
        for(Cell cell : updates.keySet()) {
            updater.update(cell.getX(), cell.getY(), updates.get(cell));
        }
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

    public void setUpdater(Updater updater){
        this.updater = updater;
    }
}