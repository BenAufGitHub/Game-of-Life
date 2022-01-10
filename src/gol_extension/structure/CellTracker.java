package gol_extension.structure;

import gol_extension.updates.Updates;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

/**
 * this class determines which Cells need to be changed, changes the Cells accordingly.
 * It keeps Cell updates in a log, so that the Game Of Life can access those and send update requests to GUI
 * Most of Game of Lifes "technical" methods are rebased into this bundled class
 * Has Power over how the games rules are set up (as of now: classic GOL)
 */
public class CellTracker {
    private Grid grid;
    private HashSet<Cell> tracked = new HashSet<>();
    private HashSet<Cell> forChange = new HashSet<>();
    private ArrayDeque<HashMap<Cell, Updates>> log = new ArrayDeque<>();


    public CellTracker(Grid grid){
        this.grid = grid;
    }


    /**
     * called by game, once per game act
     */
    public void act(){
        log.addLast(new HashMap<>());     // <- new Map for new Updates
        trackGridChanges();
        loadNext();
    }


    /**
     * every Cell in reviewList will be prospect to this evaluation.
     * if Cell need to be changed (dead <=> alive) -> listed in forChange (HashSet)
     */
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


    /**
     * if cell is in the latest update log, this return true
     */
    public boolean latelyChanged(Cell cell){
        return cell != null && log.peekLast().containsKey(cell);
    }


    private void loadNext(){
        for(Cell cell: forChange){
            change(cell);

            if(cell.isAlive()){
                track(cell.getNeighbours());
                log.getLast().put(cell, Updates.LIVE);
            }
            else {
                log.getLast().put(cell, Updates.DIE);
            }

        }
        cleanReviewList();
    }


    /**
     * called by GOL clicked method
     */
    public void clicked(int x, int y){
        log.addLast(new HashMap<>());
        if(y >= getGrid().getHeight() || x >= getGrid().getWidth())
            return;
        Cell cell = grid.getCell(x,y);
        if(cell.isAlive()){
            cell.setAlive(false);
            log.getLast().put(cell, Updates.DIE);
            cleanReviewList();
            return;
        }
        cell.setAlive(true);
        track(cell);
        log.getLast().put(cell, Updates.LIVE);
        track(cell.getNeighbours());
    }


    public void track(Cell... cells) {
        for(Cell cell : cells){
            if(!getReviewList().contains(cell)){
                getReviewList().add(cell);
                cell.setTracked(true);
                log.getLast().put(cell, Updates.NEW);
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
            log.getLast().put(cell, Updates.DELETE);
        }
    }


    public ArrayDeque<HashMap<Cell, Updates>> getUpdateLog(){
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
     * deletes all saved Cells and rescans extension.Grid for tracking Cells
     */
    public void revalidate(){
        log.add(new HashMap<>());
        for(Cell cell : getReviewList())
            log.peekLast().put(cell, Updates.DELETE);
        getReviewList().clear();
        for(int x=0; x< grid.getWidth(); x++){
            for(int y=0; y< grid.getHeight(); y++){
                Cell cell = grid.getCell(x, y);
                if(cell.isAlive() || cell.getCompany() > 0){
                    getReviewList().add(cell);
                    log.peekLast().put(cell, Updates.NEW);
                }
            }
        }
        log.add(new HashMap<>());
        for(Cell cell : getReviewList()){
            if(cell.isAlive())
                log.getLast().put(cell, Updates.LIVE);
        }
    }



}
