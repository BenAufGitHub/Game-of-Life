package gol_extension.updates;

import structure.Blueprint;
import structure.Output;


/**
 * this class is used to implement a white-gray (or white-standart) clean appearance for the Game of Life GUI.
 */
public class UpdaterClean extends Updater {

    public UpdaterClean(Output out) {
        super(out);
    }


    /**
     * @param update the new state of the cell at a specified point (updates.Updates)
     * @return Color scheme that shall be used for output: Live -> White, else -> standard
     */
    @Override
    protected Blueprint getBlueprint(int x, int y, Updates update) {
        return switch(update){
            case DELETE, DIE -> getStandardBlueprint();
            case LIVE -> WHITE;
            default -> null;
        };
    }

}
