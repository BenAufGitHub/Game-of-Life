import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class CellTracker {
    private Grid grid;
    private HashSet<Cell> tracked = new HashSet();
    private HashSet<Cell> forChange = new HashSet();
    private HashMap<Cell, Update> log = new HashMap();
    //private ArrayDeque<HashMap<Cell, String>> log = new ArrayDeque();

    enum Update {
        DELETE, LIVE, DIE, NEW
    }
    // TODO turn log into ArrQueue<HashSet<Cell, String> , game can then review and drop the latest changes

    public CellTracker(Grid grid){
        this.grid = grid;
    }


    /**
     * called by game, once per game act
     */
    public void act(){
        log = new HashMap<>();
        trackGridChanges();
        loadNext();
    }


    private void trackGridChanges(){
        forChange.clear();
        for(Cell cell: getReviewList()){
            int companions = cell.getCompany();
            if(cell.isAlive() && (companions<2 || companions>3))
                forChange.add(cell);
            if(!cell.isAlive() && companions == 3)
                forChange.add(cell);
        }
    }

    public boolean latelyChanged(Cell cell){
        return false; //log.peak().contains(cell);
    }


    private void loadNext(){
        for(Cell cell: forChange){
            change(cell);

            if(cell.isAlive()){
                track(cell.getNeighbours());
                log.put(cell, Update.LIVE);
            }
            else {
                log.put(cell, Update.DIE);
            }

        }
        cleanReviewList();
    }


    public void clicked(int x, int y){
        log = new HashMap<>();
        if(y >= getGrid().getHeight() || x >= getGrid().getWidth())
            return;
        Cell cell = grid.getCell(x,y);
        if(cell.isAlive()){
            cell.setAlive(false);
            log.put(cell, Update.DIE);
            cleanReviewList();
            return;
        }
        cell.setAlive(true);
        track(cell);
        log.put(cell, Update.LIVE);
        track(cell.getNeighbours());
    }

    public void track(Cell... cells) {
        for(Cell cell : cells){
            if(!getReviewList().contains(cell)){
                getReviewList().add(cell);
                cell.setTracked(true);
                log.put(cell, Update.NEW);
            }
        }
    }

    private boolean change(Cell cell){
        if(cell.isAlive()){
            cell.setAlive(false);
            return false;
        }
        cell.setAlive(true);
        return true;
    }

    public HashSet<Cell> getReviewList(){
        return tracked;
    }

    public void cleanReviewList(){
        HashSet<Cell> dump = new HashSet<>();
        for(Cell cell : getReviewList()){
            if(!cell.isAlive() && cell.getCompany() == 0)
                dump.add(cell);
        }
        for(Cell cell : dump){
            getReviewList().remove(cell);
            cell.setTracked(false);
            log.put(cell, Update.DELETE);
        }
    }


    public HashMap<Cell, Update> getUpdateLog(){
        return log;
    }


    public void setGrid(Grid grid){
        this.grid = grid;
        revalidate();
    }

    public Grid getGrid(){
        return grid;
    }


    /**
     * deletes all saved Cells and rescans Grid for tracking Cells
     */
    public void revalidate(){
        for(Cell cell : getReviewList())
            log.put(cell, Update.DELETE);
        getReviewList().clear();
        for(int x=0; x< grid.getWidth(); x++){
            for(int y=0; y< grid.getHeight(); y++){
                Cell cell = grid.getCell(x, y);
                if(cell.isAlive() || cell.getCompany() > 0){
                    getReviewList().add(cell);
                    log.put(cell, Update.NEW);
                }
            }
        }
        //log.append(new HashSet<>())
        for(Cell cell : getReviewList()){
            if(cell.isAlive())
                log.put(cell, Update.LIVE);
        }
    }




    //------------------------------- public access Cell HashSets -----------------------------

}
