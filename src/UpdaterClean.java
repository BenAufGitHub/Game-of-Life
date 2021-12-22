import structure.Blueprint;
import structure.Output;

public class UpdaterClean extends UpdaterGOL {

    public UpdaterClean(Output out) {
        super(out);
    }


    @Override
    protected Blueprint getBlueprint(int x, int y, Updates update) {
        return switch(update){
            case DELETE, DIE -> GRAY;
            case LIVE -> WHITE;
            default -> null;
        };
    }

}
