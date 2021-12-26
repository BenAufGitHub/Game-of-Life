package gol_extension.updates;

import structure.Blueprint;
import structure.Output;

public class UpdaterClean extends Updater {

    public UpdaterClean(Output out) {
        super(out);
    }


    @Override
    protected Blueprint getBlueprint(int x, int y, Updates update) {
        return switch(update){
            case DELETE, DIE -> getStandartBlueprint();
            case LIVE -> WHITE;
            default -> null;
        };
    }

}
