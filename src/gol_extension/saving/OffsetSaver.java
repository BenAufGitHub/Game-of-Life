package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONObject;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class OffsetSaver extends GOL_JSONSaver{

    // Margins as for the insertion of the saved obj from the top left corner.
    private int xMargin = 2;
    private int yMargin = 2;


    public OffsetSaver(GameOfLife game) {
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
     * Calculates the offset + margin into the Coordinates if they exist.
     */
    protected Point getCoordinates(JSONObject obj) {
        long x = (long) obj.get("x");
        long y = (long) obj.get("y");

        if(obj.containsKey("offset")){
            JSONObject offset = (JSONObject) obj.get("offset");
            x -= ((long) offset.get("x")) - xMargin;
            y -= ((long) offset.get("y")) - yMargin;
        }
        return new Point((int) x, (int) y);
    }


    /**
     * Game to Elements (Save).
     */
    @Override
    protected List<JSONObject> translateGame(){
        List<Cell> aliveCells = getAliveCells();
        JSONObject offset = getOffset(aliveCells);
        return collectJSON(aliveCells, offset);
    }


    /**
     * Determines the most top left Point of the save-structure and sets that as the structures offset relative to the top left.
     */
    protected JSONObject getOffset(List<Cell> aliveCells) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        for(Cell cell : aliveCells){
            minX = Math.min(minX, cell.getX());
            minY = Math.min(minY, cell.getY());
        }
        return toJSON(minX, minY);
    }


    /**
     * alive Cells are collected as JSON
     */
    protected List<JSONObject> collectJSON(List<Cell> alive, JSONObject offset) {
        ArrayList<JSONObject> collection = new ArrayList<>();
        for(Cell cell : alive){
            JSONObject obj = toJSON(cell.getX(),cell.getY());
            obj.put("offset", offset);
            collection.add(obj);
        }
        return collection;
    }


    public void setMargin(int xMargin, int yMargin){
        if(xMargin < 0 || yMargin < 0)
            throw new RuntimeException("Cannot set margin to a negative value: "+xMargin+" and "+yMargin);
        this.xMargin = xMargin;
        this.yMargin = yMargin;
    }
}
