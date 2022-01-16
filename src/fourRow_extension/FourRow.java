package fourRow_extension;

import structure.Blueprint;
import structure.Game;

import java.awt.Color;
import java.awt.Point;

public class FourRow extends Game {

    public static final Blueprint RED_BLUEPRINT = new Blueprint(Color.RED);
    public static final Blueprint BLUE_BLUEPRINT = new Blueprint(Color.BLUE);
    private static final int SECOND = 1000;

    private TeamManager teamManager;
    private GridManager gridManager;
    private boolean inGame = false;
    private Timer timer;
    private int xCells;
    private int yCells;

    public FourRow(MyWindow op) {
        super(op);
        timer = new Timer(10);
        this.xCells = op.getXCells();
        this.yCells = op.getYCells();
        teamManager = new TeamManager();
        gridManager = new GridManager(xCells, yCells);
        configureTime(SECOND);
    }


    @Override
    public void reset() {
        // reset as new game Button
        initGame();
        gridManager.reset();
    }

    private void initGame() {
        teamManager.setTeamOnTurn(teamManager.randomTeam());
        inGame = true;
    }

    @Override
    protected void act() {
        if(!inGame){
            return;
        }
        synchronized (this){
            Point input = gridManager.popUserInput();
            Team onTurn = teamManager.getTeamOnTurn();
            if(input != null){
                click(input, onTurn);
                gridManager.placeAt(onTurn, input.x, input.y);
                if(gridManager.has4Row(onTurn)){
                    inGame = false;
                    getGUI().write(onTurn + " has won!");
                    return;
                }
                teamManager.swapTeam();
                timer.reset();
            }
        }
        getGUI().write(timer.remainingTimeToString());
        timer.tick();
    }


    private void click(Point lastClicked, Team onTurn) {
        int x = lastClicked.x;
        int y = lastClicked.y;

        if(isOccupied(x, y))
            return;
        if(onTurn == Team.RED)
            getGUI().showAction(x,y, RED_BLUEPRINT);
        else {
            getGUI().showAction(x,y, BLUE_BLUEPRINT);
        }
    }

    private boolean isOccupied(int x, int y) {
        return getGUI().getBlueprint(x,y).color != getGUI().getSettings().getStandardGridColor();
    }


    @Override
    public void onHover(int x, int y){

    }


    @Override
    public void onClick(int x, int y){
        synchronized (this){
            gridManager.setUserInput(new Point(x,y));
        }
    }


    public MyWindow getGUI(){
        return (MyWindow) this.getOutput();
    }

    public boolean isPlaying(){
        return inGame;
    }

    private void configureTime(int milliseconds) {
        getGUI().configureSpeedButton(milliseconds, milliseconds, milliseconds, milliseconds);
    }
}
