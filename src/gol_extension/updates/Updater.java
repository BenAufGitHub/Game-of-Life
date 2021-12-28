package gol_extension.updates;

import structure.Blueprint;
import structure.GUI;
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
    private Blueprint standard;

    public Output getOut(){
        return out;
    }


    public Updater(Output out) {
        this.out = out;

        if(getOut() instanceof GUI){
            Color color = ((GUI) getOut()).getSettings().getStandardGridColor();
            this.standard = new Blueprint(color, null);
            return;
        }
        this.standard = GRAY;
    }

    public void update(int x, int y, Updates update){
        Blueprint upd = getBlueprint(x,y,update);
        if(upd != null)
            getOut().showAction(x,y, upd);
    }

    protected Blueprint getStandardBlueprint(){
        return standard;
    }

    protected abstract Blueprint getBlueprint(int x, int y, Updates update);
}
