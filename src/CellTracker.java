import java.util.ArrayList;
import java.util.HashSet;

public class CellTracker {
    private Grid grid;
    private HashSet<Cell> tracked = new HashSet();
    private HashSet<Cell> forChange = new HashSet();
    private HashSet<Cell> dump = new HashSet();
    private HashSet<Cell> latestAdditions = new HashSet();

    public CellTracker(Grid grid){
        this.grid = grid;
    }


    /**
     * called by game, once per game act
     */
    public void act(){
        trackGridChanges();
        loadNext();
    }


    private void trackGridChanges(){
        for(Cell cell: getReviewList()){
            int companions = cell.getCompany();
            if(cell.isAlive() && (companions<2 || companions>3))
                forChange.add(cell);
            if(!cell.isAlive() && companions == 3)
                forChange.add(cell);
        }
    }


    private void loadNext(){
        for(Cell cell: forChange){
            change(cell);

            if(cell.isAlive())
                track(cell.getNeighbours());
        }
        forChange.clear();
        cleanReviewList();
    }


    public void clicked(int x, int y){
        Cell cell = grid.getCell(x,y);
        if(cell.isAlive()){
            cell.setAlive(false);
            cleanReviewList();
            latestAdditions.clear();
            return;
        }
        dump.clear();
        cell.setAlive(true);
        track(cell);
        track(cell.getNeighbours());
        latestAdditions.add(cell);
    }

    public void track(Cell... cells) {
        latestAdditions.clear();
        for(Cell cell : cells){
            latestAdditions.add(cell);
            getReviewList().add(cell);
            cell.setTracked(true);
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
        dump.clear();
        for(Cell cell : getReviewList()){
            if(!cell.isAlive() && cell.getCompany() == 0)
                dump.add(cell);
        }
        for(Cell cell : dump){
            getReviewList().remove(cell);
            cell.setTracked(false);
        }
    }


    public HashSet<Cell> getLatestAdditions(){
        return latestAdditions;
    }

    public HashSet<Cell> getLatestRemovals(){
        return dump;
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
        getReviewList().clear();
        for(int x=0; x< grid.getWidth(); x++){
            for(int y=0; y< grid.getHeight(); y++){
                Cell cell = grid.getCell(x, y);
                if(cell.isAlive() || cell.getCompany() > 0)
                    getReviewList().add(cell);
            }
        }
    }




    //------------------------------- public access Cell HashSets -----------------------------

}
