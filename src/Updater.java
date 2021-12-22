import structure.Output;

public abstract class Updater {
    private Output out;

    public Updater(Output out){
        this.out = out;
    }

    public Output getGUI(){
        return out;
    }

    /**
     * updates one specific Cell with a specific Update
     * @param cell
     * @param upd = DELETE, LIVE, DIE, NEW;
     */
    public abstract void update(Cell cell, CellTracker.Update upd);
}
