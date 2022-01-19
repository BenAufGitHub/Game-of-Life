package fourRow_extension;

import structure.Output;

import java.awt.Color;
import java.awt.Point;

public class HoverManager {

    private Point currentHoverPosition;
    private GridManager gridManager;
    private TeamManager teamManager;
    private MyWindow gui;


    public HoverManager(MyWindow gui, GridManager gridManager, TeamManager teamManager){
        this.gui = gui;
        this.gridManager = gridManager;
        this.teamManager = teamManager;
    }


    /**
     * This method can correct the hovering if the hovered cells has been clicked/filled.
     * By calling updateHover(), this method puts the hover a cell up, or if mouse has been moved, to the new location.
     */
    public synchronized void adjustHover(int x, int y) {
        if(currentHoverPosition != null && currentHoverPosition.x != x)
            updateHover(currentHoverPosition.x, 0);
        else // hover starts from top cell, indicates on most bottom cell
            updateHover(x, 0);
    }


    public synchronized void updateHover(int x, int y){
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
            return FourRow.LIGHT_BlUE_BLUEPRINT.color.equals(cellColor);
        return FourRow.LIGHT_RED_BLUEPRINT.color.equals(cellColor);
    }


    private void hoverWithTeamColours(int x, int y) {
        if(teamManager.getTeamOnTurn() == Team.BLUE)
            getGUI().showAction(x, y, FourRow.LIGHT_BlUE_BLUEPRINT);
        else
            getGUI().showAction(x, y, FourRow.LIGHT_RED_BLUEPRINT);
    }


    public void deleteHoverIndication(){
        //guard checks NullPointer, checks whether the current hover position has been overwritten
        if(currentHoverPosition == null || gridManager.isOccupied(currentHoverPosition.x, currentHoverPosition.y))
            return;
        getGUI().showAction(currentHoverPosition.x, currentHoverPosition.y, Output.Action.STANDARD);
    }


    private boolean isOccupied(int x, int y) {
        return getGUI().getBlueprint(x,y).color != getGUI().getSettings().getStandardGridColor();
    }

    public MyWindow getGUI(){
        return gui;
    }
}
