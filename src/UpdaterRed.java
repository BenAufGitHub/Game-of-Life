import structure.Output;

public class UpdaterRed extends UpdaterGOL{

    public UpdaterRed(Output out) {
        super(out);
    }

    @Override
    public void update(int x, int y, Updates upd) {
        switch(upd){
            case DELETE -> update(x,y, GRAY);
            case LIVE -> update(x,y, WHITE);
            case DIE, NEW -> update(x,y, RED);
        }
    }
}
