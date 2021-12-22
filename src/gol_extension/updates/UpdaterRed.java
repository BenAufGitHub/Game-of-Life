package gol_extension.updates;

import structure.Blueprint;
import structure.Output;

public class UpdaterRed extends Updater {

    public UpdaterRed(Output out) {
        super(out);
    }

    @Override
    protected Blueprint getBlueprint(int x, int y, Updates update) {
        return switch(update){
            case DELETE -> GRAY;
            case LIVE -> WHITE;
            case DIE, NEW -> RED;
            default -> null;
        };
    }


}
