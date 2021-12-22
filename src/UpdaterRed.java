import structure.Blueprint;
import structure.Output;

public class UpdaterRed extends UpdaterGOL{

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
