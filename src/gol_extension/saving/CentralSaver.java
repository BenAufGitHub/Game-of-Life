package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONObject;

import java.awt.Point;
import java.util.List;


/**
 * positions all loaded structures centered into the Grid.
 */
public class CentralSaver extends OffsetSaver{

    private int minOffsetX = STANDARD_X_MARGIN;
    private int minOffsetY = STANDARD_Y_MARGIN;


    public CentralSaver(GameOfLife game) {
        super(game);
    }


    /**
     * Elements to game (load).
     */
    @Override
    protected void translateElements(List<JSONObject> objects){
        for(JSONObject obj : objects){
            Point point = getCoordinates(obj);
            getGame().clicked(point.x, point.y);
        }
    }


    /**
     * if objects has a maxOffset Key, this calcs the Point in a relative, centered position
     */
    @Override
    protected Point getCoordinates(JSONObject obj) {
        if(obj.containsKey("max_offset")) {
            setMargin(0,0);
            Point rawPoint = super.getCoordinates(obj);
            return getRelativeLocation(rawPoint, (JSONObject) obj.get("offset"), (JSONObject) obj.get("max_offset"));
        }
        setMargin(STANDARD_X_MARGIN, STANDARD_Y_MARGIN);
        return super.getCoordinates(obj);
    }


    private Point getRelativeLocation(Point rawPoint, JSONObject offset, JSONObject max_offset) {
         long minX = (long) offset.get("x");
         long minY = (long) offset.get("y");
         long maxX = (long) max_offset.get("x");
         long maxY = (long) max_offset.get("y");

         // Construct bounds
         long width = maxX -minX;
         long height = maxY - minY;

         // Margin in order to center
         int xShift = (getGame().getWidth() - ((int) width)) / 2;
         int yShift = (getGame().getHeight() - ((int) height)) / 2;

         xShift = Math.max(xShift, minOffsetX);
         yShift = Math.max(yShift, minOffsetY);

         return new Point(rawPoint.x + xShift, rawPoint.y + yShift);
    }


    /**
     * Game to Elements (Save).
     */
    @Override
    protected List<JSONObject> translateGame(){
        List<Cell> aliveCells = getAliveCells();
        JSONObject minOffset = getOffset(aliveCells);
        JSONObject maxOffset = getMaxOffset(aliveCells);
        return collectJSON(aliveCells, minOffset, maxOffset);
    }


    protected List<JSONObject> collectJSON(List<Cell> alive, JSONObject offset, JSONObject maxOffset){
        List<JSONObject> objects = super.collectJSON(alive, offset);
        for(JSONObject obj : objects){
            obj.put("max_offset", maxOffset);
        }
        return objects;
    }


    /**
     * Determines the most top left Point of the save-structure and sets that as the structures offset relative to the top left.
     */
    protected JSONObject getMaxOffset(List<Cell> aliveCells) {
        int maxX = 0;
        int maxY = 0;
        for(Cell cell : aliveCells){
            maxX = Math.max(maxX, cell.getX());
            maxY = Math.max(maxY, cell.getY());
        }
        return toJSON(maxX, maxY);
    }


    public void setMinimumRelativeMargin(int x, int y){
        minOffsetX = x;
        minOffsetY = y;
    }
}
