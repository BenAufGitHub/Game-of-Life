package fourRow_extension;

import structure.Blueprint;
import structure.Game;
import structure.Output;

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
    private boolean inGame = false;
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
        currentHoverPosition = null;
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
        else{
            changeTeam();
            adjustHover(input.x, input.y);
        }
    }

    private void adjustHover(int x, int y) {
        if(currentHoverPosition != null && currentHoverPosition.x != x)
            onHover(currentHoverPosition.x, 0);
        else // hover starts from top cell, indicates on most bottom cell
            onHover(x, 0);
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
        getGUI().write( teamManager.getTeamOnTurn() + ": "+ timer.remainingTimeToString());
        timer.tick();
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
        if(isOccupied(x,y))
            return;
        Point dropOff = gridManager.calcDropOffPosition(x, y);
        if(dropOff != null && dropOff.equals(currentHoverPosition) && hoverColorsCorrect())
            return;
        deleteHoverIndication();
        currentHoverPosition = dropOff;
        if(dropOff != null)
            hoverWithTeamColours(dropOff.x,dropOff.y);
    }

    private boolean hoverColorsCorrect() {
        if(currentHoverPosition == null)
            return false;
        Color cellColor = getGUI().getBlueprint(currentHoverPosition.x, currentHoverPosition.y).color;
        if(teamManager.getTeamOnTurn() == Team.BLUE)
            return LIGHT_BlUE_BLUEPRINT.color.equals(cellColor);
        return LIGHT_RED_BLUEPRINT.color.equals(cellColor);
    }


    private void hoverWithTeamColours(int x, int y) {
        if(teamManager.getTeamOnTurn() == Team.BLUE)
            getGUI().showAction(x, y, LIGHT_BlUE_BLUEPRINT);
        else
            getGUI().showAction(x, y, LIGHT_RED_BLUEPRINT);
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


    /**
     * @param milliseconds at least 600 required.
     */
    private void configureTime(int milliseconds) {
        getGUI().configureSpeedButton(milliseconds+500, milliseconds, milliseconds-300, milliseconds-600);
    }


    public void deleteHoverIndication(){
        //guard checks NullPointer, checks whether the current hover position has been overwritten
        if(currentHoverPosition == null || gridManager.isOccupied(currentHoverPosition.x, currentHoverPosition.y))
            return;
        getGUI().showAction(currentHoverPosition.x, currentHoverPosition.y, Output.Action.STANDARD);
    }
}
