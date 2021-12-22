import structure.Blueprint;
import structure.Output;

import java.awt.*;

public abstract class Updater {
    private Output out;

    public Updater(Output out){
        this.out = out;
    }

    public Output getOut(){
        return out;
    }

    /**
     * updates one specific Cell with a specific Update
     */
    public abstract void update(int x, int y, Blueprint blueprint);
}
