import structure.*;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;

public class GameOfLife extends Game {
    private final static Image circle = new ImageIcon("resources//black_circle.png").getImage();
    private final static Color tracked = Color.RED;
    private final static Blueprint live = new Blueprint(tracked, circle);
    private final static Blueprint clear = new Blueprint(Color.GRAY, null);
    private final static Blueprint track = new Blueprint(Color.RED, null);

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
        //TODO

    }

    @Override
    protected void act() {

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
