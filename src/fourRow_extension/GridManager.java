package fourRow_extension;

import fourRow_extension.grid.Direction;
import fourRow_extension.grid.Grid;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

public class GridManager {
    private Point userInput;
    private Grid<Team> grid;

    private Set<Point> winningSequence;
    private boolean blueWon;
    private boolean redWon;

    private int columns;
    private int rows;

    private enum Alignment {
        HORIZONTAL, VERTICAL, DIAGONAL1, DIAGONAL2
    }


    public GridManager(int columns, int rows){
        this.columns = columns;
        this.rows = rows;
        initObjectAttributes();
    }


    private void initObjectAttributes() {
        this.grid = new Grid<>(columns, rows);
        this.winningSequence = new HashSet<>();
        this.userInput = null;
    }


    public void setUserInput(Point selected){
        synchronized (this){
            if(userInput!=null)
                return;
            if(selected == null || isOccupied(selected.x, selected.y))
                return;
            userInput = selected;
        }
    }


    public Point popUserInput(){
        synchronized (this) {
            Point val = userInput;
            userInput = null;
            return val;
        }
    }


    public void placeAt(Team team, int x, int y){
        grid.moveTo(x, y);
        grid.get().setContent(team);
        if(has4RowAtPointerLocation() == true){
            if(team == Team.BLUE)
                blueWon = true;
            else
                redWon = true;
        }
    }


    public void reset(){
        initObjectAttributes();
        blueWon = false;
        redWon = false;
    }


    public Set<Point> getWinningSequence(){
        return winningSequence;
    }

    public Team getTeam(int x, int y){
        grid.moveTo(x,y);
        return grid.get().getContent();
    }


    public boolean isOccupied(int x, int y){
        grid.moveTo(x,y);
        return grid.get().getContent() != null;
    }


    public boolean has4Row(Team team){
        if(team == Team.BLUE)
            return blueWon;
        return redWon;
    }


    public Point calcDropOffPosition(int x, int y) {
        grid.moveTo(x,y);
        if(grid.get().getContent() != null) // already occupied
            return null;
        while(grid.peek(Direction.DOWN) != null && grid.peek(Direction.DOWN).getContent() == null)
            grid.move(Direction.DOWN);
        return new Point(grid.get().getX(), grid.get().getY());
    }


    /**
     * @return True if all GridNotes are filled with content (so not null), also true if grid dimensions are smaller than (1,1).
     */
    public boolean isGridFull(){
        if(rows == 0 || columns == 0)
            return true;
        for(int x=0; x<columns; x++){
            grid.moveTo(x, 0);
            if(grid.get().getContent() == null)
                return false;
        }
        return true;
    }


    // ----------------------------------------- background operations ----------------------------------------------------------


    private boolean has4RowAtPointerLocation() {
        if(grid.get() == null)
            return false;
        Team team = grid.get().getContent();
        int horizontal = 1 + checkLineFromPointer(team, Direction.RIGHT) + checkLineFromPointer(team, Direction.LEFT);
        int vertical = 1 + checkLineFromPointer(team, Direction.DOWN) + checkLineFromPointer(team, Direction.UP);
        int dig1 = 1 + checkLineFromPointer(team, Direction.DOWN_LEFT) + checkLineFromPointer(team, Direction.UP_RIGHT);
        int dig2 = 1 + checkLineFromPointer(team, Direction.UP_LEFT) + checkLineFromPointer(team, Direction.DOWN_RIGHT);

        if(4 <= horizontal)
            addToWinningSequence(Alignment.HORIZONTAL);
        if(4 <= vertical)
            addToWinningSequence(Alignment.VERTICAL);
        if(4 <= dig1)
            addToWinningSequence(Alignment.DIAGONAL1);
        if(4 <= dig2)
            addToWinningSequence(Alignment.DIAGONAL2);

        if(Math.max(horizontal, vertical) >= 4 || Math.max(dig1,  dig2) >= 4)
            return true;
        return false;
    }


    private int checkLineFromPointer(Team team, Direction dir){
        if(grid.get() == null)
            return 0;
        int x = grid.get().getX();
        int y = grid.get().getY();
        grid.move(dir);
        int result = checkLine(team, dir);
        grid.moveTo(x,y);
        return result;
    }


    private int checkLine(Team team, Direction dir){
        if(grid.get() == null || !team.equals(grid.get().getContent()))
            return 0;
        if(grid.peek(dir) == null)
            return 1;
        grid.move(dir);
        return 1 + checkLine(team, dir);
    }


    private void addToWinningSequence(Alignment alignment) {
        Team team = grid.get().getContent();
        int initialX = grid.get().getX();
        int initialY = grid.get().getY();
        switch (alignment) {
            case HORIZONTAL:
                recursiveWinningSequenceFinding(Direction.RIGHT, team);
                recursiveWinningSequenceFinding(Direction.LEFT, team);
                break;
            case VERTICAL:
                recursiveWinningSequenceFinding(Direction.UP, team);
                recursiveWinningSequenceFinding(Direction.DOWN, team);
                break;
            case DIAGONAL1:
                recursiveWinningSequenceFinding(Direction.DOWN_LEFT, team);
                recursiveWinningSequenceFinding(Direction.UP_RIGHT, team);
                break;
            case DIAGONAL2:
                recursiveWinningSequenceFinding(Direction.DOWN_RIGHT, team);
                recursiveWinningSequenceFinding(Direction.UP_LEFT, team);
                break;
        }
        grid.moveTo(initialX, initialY);
    }


    private void recursiveWinningSequenceFinding(Direction dir, Team team){
        if(grid.get().getContent() == team)
            winningSequence.add(new Point(grid.get().getX(), grid.get().getY()));
        if(grid.peek(dir) == null || grid.peek(dir).getContent() != team)
            return;
        grid.move(dir);
        recursiveWinningSequenceFinding(dir, team);
    }
}
