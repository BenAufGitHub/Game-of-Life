package gol_extension.updates;

import structure.Blueprint;
import structure.Output;

import java.awt.Color;

/**
 * A custom extension.gol_updates.Updater made for Game of Life's GUI
 */
public abstract class Updater {
    protected final static Blueprint WHITE = new Blueprint(Color.WHITE, null);
    protected final static Blueprint GRAY = new Blueprint(Color.GRAY, null);
    protected final static Blueprint RED = new Blueprint(Color.RED, null);

    private Output out;

    public Output getOut(){
        return out;
    }


    public Updater(Output out) {
        this.out = out;
    }

    public void update(int x, int y, Updates update){
        Blueprint upd = getBlueprint(x,y,update);
        if(upd != null)
            getOut().showAction(x,y, upd);
    }

    protected abstract Blueprint getBlueprint(int x, int y, Updates update);
}
