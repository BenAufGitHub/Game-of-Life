import structure.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.HashSet;

public class GameOfLife extends Game {
    private final static Image circle = new ImageIcon("resources//black_circle.png").getImage();
    private final static Color tracked = Color.RED;
    private final static Blueprint live = new Blueprint(tracked, circle);
    private final static Blueprint die  = new Blueprint(null, null);
    private final static Blueprint clear = new Blueprint(Color.GRAY, null);
    private final static Blueprint track = new Blueprint(tracked);

    CellTracker cellTracker;

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

    @Override
    public void reset() {
        getCellTracker().setGrid(new Grid(getOutput().gridWidth(), getOutput().gridHeight()));
        getOutput().clear();
    }

    @Override
    public void clicked(int x, int y) {
        if(running())
            return;
        getCellTracker().clicked(x,y);
        if(getCellTracker().getLatestAdditions().size() > 0){
            getOutput().showAction(x,y, live);
            for(Cell c: getCellTracker().getLatestAdditions()){
                if(!c.isAlive())
                    getOutput().showAction(c.getX(),c.getY(), track);
            }
            return;
        }
        if(getCellTracker().getGrid().getCell(x,y).getTracked())
            getOutput().showAction(x,y, new Blueprint(Color.RED, null));
        else
            getOutput().showAction(x,y, clear);
        for(Cell c : cellTracker.getLatestRemovals()){
            getOutput().showAction(c.getX(),c.getY(), clear);
        }
    }


    @Override
    protected void act() {
        getCellTracker().act();
        HashMap<Cell, CellTracker.Update> log = getCellTracker().getUpdateLog();
        for(Cell cell : log.keySet()) {
            CellTracker.Update upd = log.get(cell);
            switch(upd){
                case NEW -> getOutput().showAction(cell.getX(),cell.getY(), track);
                case DELETE -> getOutput().showAction(cell.getX(),cell.getY(), clear);
                case LIVE -> getOutput().showAction(cell.getX(),cell.getY(), live);
                case DIE -> getOutput().showAction(cell.getX(),cell.getY(), die);
            }
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
}
