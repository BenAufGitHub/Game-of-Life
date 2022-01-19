package fourRow_extension;

import structure.Blueprint;
import structure.Game;

import java.awt.Color;
import java.awt.Point;

public class FourRow extends Game {

    public static final Blueprint RED_BLUEPRINT = new Blueprint(Color.RED);
    public static final Blueprint BLUE_BLUEPRINT = new Blueprint(Color.BLUE);
    public static final Blueprint YELLOW_BLUEPRINT = new Blueprint(Color.YELLOW);
    public static final Blueprint LIGHT_BlUE_BLUEPRINT = new Blueprint(new Color(83, 134, 173));
    public static final Blueprint LIGHT_RED_BLUEPRINT = new Blueprint(new Color(201, 94, 113));
    private static final int SECOND = 1000;

    private TeamManager teamManager;
    private GridManager gridManager;
    private HoverManager hoverManager;
    private boolean inGame = false;
    private Team winner = null;
    private boolean teamsToBeSwapped = false;
    private Timer timer;
    private int xCells;
    private int yCells;
    private Point currentHoverPosition;

    public FourRow(MyWindow op) {
        super(op);
        timer = new Timer(10);
        this.xCells = op.getXCells();
        this.yCells = op.getYCells();
        teamManager = new TeamManager();
        gridManager = new GridManager(xCells, yCells);
        hoverManager = new HoverManager(getGUI(), gridManager, teamManager);
        configureTime(SECOND);
        op.write("Four Connect - Start me!");
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
        currentHoverPosition = null;
        winner = null;
        teamsToBeSwapped = false;
        setInGame(true);
    }


    @Override
    protected void act() {
        if(!isInGame())
            return;
        synchronized (this){
            Point input = gridManager.popUserInput();
            if(input != null)
                onInput(input);

            if(winner != null)
                endGameByWinner(teamManager.getTeamOnTurn());
            else if(gridManager.isGridFull())
                endGameByTie();
            else if(teamsToBeSwapped){
                changeTeam();
                hoverManager.adjustHover(input.x, input.y);
            }
            else if(timer.timeUp())
                endGameByTime();
            if(isInGame())
                tick();
        }
    }


    private void onInput(Point input){
        Team onTurn = teamManager.getTeamOnTurn();
        dropDiskOffAt(input, onTurn);
        if(gridManager.has4Row(onTurn))
            winner = onTurn;
        else
            teamsToBeSwapped = true;
    }


    private void changeTeam() {
        teamManager.swapTeam();
        timer.reset();
        teamsToBeSwapped = false;
    }

    private void endGameByWinner(Team winner) {
        setInGame(false);
        getGUI().triggerStopButton();
        colorWinningSequence();
        getGUI().write(winner + " has won!");
    }


    private void endGameByTime() {
        setInGame(false);
        getGUI().triggerStopButton();
        Team t = teamManager.getTeamOnTurn();
        getGUI().write(t +" lost, time's up!");
    }


    private void endGameByTie(){
        setInGame(false);
        getGUI().triggerStopButton();
        getGUI().write("OOPS, A TIE");
    }


    /**
     * @param milliseconds at least 600 required.
     */
    private void configureTime(int milliseconds) {
        getGUI().configureSpeedButton(milliseconds+1000, milliseconds, milliseconds-300, milliseconds-600);
    }


    private void colorWinningSequence() {
        for(Point p : gridManager.getWinningSequence()){
            getGUI().showAction(p.x, p.y, YELLOW_BLUEPRINT);
        }
    }


    /**
     * Connect-Four uses gravity, so dropping your disk means putting it into the lowest Cell possible.
     * This method does exactly that.
     */
    private void dropDiskOffAt(Point input, Team onTurn) {
        // Synchronization prevents hovering effect from reading (simultaneously set) occupied cell as free to show as hovered.
        synchronized(hoverManager){
            Point position = gridManager.calcDropOffPosition(input.x, input.y);
            click(position, onTurn);
            gridManager.occupy(onTurn, position.x, position.y);
        }
    }


    private void click(Point lastClicked, Team onTurn) {
        int x = lastClicked.x;
        int y = lastClicked.y;
        if(onTurn == Team.RED)
            getGUI().showAction(x,y, RED_BLUEPRINT);
        else {
            getGUI().showAction(x,y, BLUE_BLUEPRINT);
        }
    }


    private void tick(){
        getGUI().write( teamManager.getTeamOnTurn() + ": "+ timer.remainingTimeToString());
        timer.tick();
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
        synchronized (hoverManager){
            hoverManager.updateHover(x,y);
        }
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
}
