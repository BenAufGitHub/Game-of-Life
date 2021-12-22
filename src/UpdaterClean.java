import structure.Output;

public class UpdaterClean extends UpdaterGOL{

    public UpdaterClean(Output out) {
        super(out);
    }


    @Override
    public void update(int x, int y, Updates upd) {
        switch(upd){
            case DELETE, DIE -> update(x,y, GRAY);
            case LIVE -> update(x, y, WHITE);
        }
    }
}
