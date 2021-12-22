import structure.Output;

public class UpdaterRed extends Updater{

    public UpdaterRed(Output out) {
        super(out);
    }

    @Override
    public void update(Cell cell, CellTracker.Update upd) {
        switch(upd){
            case DELETE -> getOut().showAction(cell.getX(),cell.getY(), GRAY);
            case LIVE -> getOut().showAction(cell.getX(),cell.getY(), WHITE);
            case DIE, NEW -> getOut().showAction(cell.getX(), cell.getY(), RED);
        }
    }
}
