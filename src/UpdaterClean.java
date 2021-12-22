import structure.GUI;

public class UpdaterClean extends Updater{

    public UpdaterClean(GUI gui) {
        super(gui);
    }

    @Override
    public void update(Cell cell, CellTracker.Update upd) {
        switch(upd){
            case DELETE, DIE -> getGUI().showAction(cell.getX(),cell.getY(), GRAY);
            case LIVE -> getGUI().showAction(cell.getX(),cell.getY(), WHITE);
        }
    }
}
