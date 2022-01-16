package fourRow_extension;

import structure.Blueprint;
import structure.Game;

import java.awt.Color;
import java.awt.Point;

public class FourRow extends Game {

    public static final Blueprint RED_BLUEPRINT = new Blueprint(Color.RED);
    public static final Blueprint BLUE_BLUEPRINT = new Blueprint(Color.BLUE);
    public static final Blueprint YELLOW_BLUEPRINT = new Blueprint(Color.YELLOW);
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
        timer.reset();
        setInGame(true);
    }


    @Override
    protected void act() {
        if(!isInGame())
            return;
        synchronized (this){
            Point input = gridManager.popUserInput();
            if(input != null)
                scan(input);
            if(isInGame())
                tick();
        }
    }


    private void scan(Point input){
        Team onTurn = teamManager.getTeamOnTurn();
        dropDiskOffAt(input, onTurn);
        if(gridManager.has4Row(onTurn))
            ifWon(onTurn);
        else
            changeTeam();
    }

    private void changeTeam() {
        teamManager.swapTeam();
        timer.reset();
    }

    private void ifWon(Team onTurn) {
        setInGame(false);
        getGUI().triggerStopButton();
        colorWinningSequence();
        getGUI().write(onTurn + " has won!");
    }


    private void colorWinningSequence() {
        for(Point p : gridManager.getWinningSequence()){
            getGUI().showAction(p.x, p.y, YELLOW_BLUEPRINT);
        }
    }


    /**
     * Connect-Four uses gravity, so dropping your disk means putting it into the lowest Cell possible.
     * This method does excactly that.
     */
    private void dropDiskOffAt(Point input, Team onTurn) {
        Point position = gridManager.calcDropOffPosition(input.x, input.y);
        click(position, onTurn);
        gridManager.placeAt(onTurn, position.x, position.y);
    }


    private void tick(){
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


    public void setInGame(boolean bool){
        synchronized (this) {
            inGame = bool;
        }
    }


    public boolean isInGame(){
        synchronized (this) {
            return inGame;
        }
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

    private void configureTime(int milliseconds) {
        getGUI().configureSpeedButton(milliseconds, milliseconds, milliseconds, milliseconds);
    }
}
