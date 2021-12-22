import structure.Blueprint;
import structure.Output;

import java.awt.*;

public abstract class Updater {
    protected final static Blueprint WHITE = new Blueprint(Color.WHITE, null);
    protected final static Blueprint GRAY = new Blueprint(Color.GRAY, null);
    protected final static Blueprint RED = new Blueprint(Color.RED, null);
    private Output out;

    public Updater(Output out){
        this.out = out;
    }

    public Output getOut(){
        return out;
    }

    /**
     * updates one specific Cell with a specific Update
     * @param cell
     * @param upd = DELETE, LIVE, DIE, NEW;
     */
    public abstract void update(Cell cell, CellTracker.Update upd);
}
