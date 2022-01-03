package gol_extension.updates;

import structure.Blueprint;
import structure.Output;


/**
 * this class differs from UpdaterClean-look in that it also colours tracked dead Cells red.
 */
public class UpdaterRed extends Updater {

    public UpdaterRed(Output out) {
        super(out);
    }


    /**
     * @param update the new state of the cell at a specified point (updates.Updates)
     * @return Color scheme that shall be used for output: Live -> White, else -> standard
     */
    @Override
    protected Blueprint getBlueprint(int x, int y, Updates update) {
        return switch(update){
            case DELETE -> getStandardBlueprint();
            case LIVE -> WHITE;
            case DIE, NEW -> RED;
            default -> null;
        };
    }


}
