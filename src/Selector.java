public class Selector{
    CellTracker tracker;
    public Selector(CellTracker tracker){
        this.tracker = tracker;
    }
    public void preselect(){
        /*select(0,1);
        select(1,0);
        select(2,1);
        select(2,3);
        */
        select(1,1);

        System.out.println(tracker.getGrid());
        tracker.allTrackedToTrue();
    }

    public void select(int x, int y){
        Cell cell = tracker.getGrid().getCell(x, y);
        cell.setAlive(true);

        tracker.visualizeChange(cell);
        tracker.track(cell);
        tracker.track(cell.getNeighbours());
    }
}
