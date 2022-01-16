package fourRow_extension;

import fourRow_extension.grid.Direction;
import fourRow_extension.grid.Grid;

import java.awt.Point;

public class GridManager {
    private Point userInput;
    private Grid<Team> grid;

    private boolean blueWon;
    private boolean redWon;

    private int columns;
    private int rows;


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


    public GridManager(int columns, int rows){
        this.columns = columns;
        this.rows = rows;
        initObjectAttributes();
    }


    private void initObjectAttributes() {
        this.grid = new Grid<>(columns, rows);
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

    private boolean has4RowAtPointerLocation() {
        if(grid.get() == null)
            return false;
        Team team = grid.get().getContent();
        int horizontal = 1 + checkLineFromPointer(team, Direction.RIGHT) + checkLineFromPointer(team, Direction.LEFT);
        int vertical = 1 + checkLineFromPointer(team, Direction.DOWN) + checkLineFromPointer(team, Direction.UP);
        int dig1 = 1 + checkLineFromPointer(team, Direction.DOWN_LEFT) + checkLineFromPointer(team, Direction.UP_RIGHT);
        int dig2 = 1 + checkLineFromPointer(team, Direction.UP_LEFT) + checkLineFromPointer(team, Direction.DOWN_RIGHT);

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


    //TODO for debugging
    private void printPointerInfo(){
        System.out.println("\nPositioned at: "+grid.get().getX()+", "+grid.get().getY());
        System.out.println("Team: "+grid.get().getContent()+"\n");
    }


    private int checkLine(Team team, Direction dir){
        if(grid.get() == null || !team.equals(grid.get().getContent()))
            return 0;
        if(grid.peek(dir) == null)
            return 1;
        grid.move(dir);
        return 1 + checkLine(team, dir);
    }


    public void reset(){
        initObjectAttributes();
        blueWon = false;
        redWon = false;
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


    public void printGrid(){
        System.out.println();
        Object[][] teamGrid = grid.toMatrix();
        for(int y=0; y<teamGrid.length; y++){
            for(int x=0; x<teamGrid[y].length; x++)
            System.out.println();
        }
    }
}
