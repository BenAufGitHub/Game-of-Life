import structure.Output;

public class UpdaterClean extends Updater{

    public UpdaterClean(Output out) {
        super(out);
    }

    @Override
    public void update(Cell cell, CellTracker.Update upd) {
        switch(upd){
            case DELETE, DIE -> getOut().showAction(cell.getX(),cell.getY(), GRAY);
            case LIVE -> getOut().showAction(cell.getX(),cell.getY(), WHITE);
        }
    }
}
