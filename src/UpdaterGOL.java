import structure.Blueprint;
import structure.Output;

import java.awt.*;

/**
 * A custom Updater made for Game of Life's GUI
 */
public abstract class UpdaterGOL extends Updater{
    protected final static Blueprint WHITE = new Blueprint(Color.WHITE, null);
    protected final static Blueprint GRAY = new Blueprint(Color.GRAY, null);
    protected final static Blueprint RED = new Blueprint(Color.RED, null);

    public UpdaterGOL(Output out) {
        super(out);
    }

    public abstract void update(int x, int y, Updates update);

    public void update(int x, int y, Blueprint blueprint){
        getOut().showAction(x, y, blueprint);
    }
}
