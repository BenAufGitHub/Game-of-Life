package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONObject;

import java.awt.Point;
import java.util.List;


/**
 * Loads save structures so that they appear as far in the top-left corner as possible (+margins).
 * Can read saves from superclass.
 * Adds a header to the save file.
 * Reading superclass description recommended.
 * Structure:
 *       List:
 *           [0]: JSONObject:
 *                   {
 *                       "header": {"offset": coordinates}
 *                       "contents": {coordinates0, coordinates1, ... , coordinatesX
 *                   }
 **/
public class OffsetSaver extends GOL_JSONSaver{

    // Margins as for the insertion of the saved obj from the top left corner.
    public final int STANDARD_X_MARGIN = 2;
    public final int STANDARD_Y_MARGIN = 2;

    private int xMargin = STANDARD_X_MARGIN;
    private int yMargin = STANDARD_Y_MARGIN;


    public OffsetSaver(GameOfLife game) {
        super(game);
    }


    //------------------------------- save file -> game transformation --------------------------------------------


    /**
     * Elements to game (load).
     */
    @Override
    protected void translateElements(List<JSONObject> objects){
        Point shift = calcShift(objects.get(0));
        for(JSONObject obj : retrieveContentsFromRootObject(objects.get(0))){
            Point coordinates = getJSONCoordinates(obj);
            Point newCoordinates = getCoordinates(coordinates.x, coordinates.y, shift.x, shift.y);
            getGame().clicked(newCoordinates.x, newCoordinates.y);
        }
    }


    /**
     * Calculates with which x and y the structure shall be shifted by using the JSON roots contents.
     * Override for custom behaviour.
     */
    protected Point calcShift(JSONObject root) {
        JSONObject minOffset = readHeaderAttr(root, "offset");
        if(minOffset != null){
            Point p = getJSONCoordinates(minOffset);
            return new Point(xMargin - p.x, yMargin - p.y);
        } //using no shift if json had no header with offset specified.
        return new Point(0,0);
    }


    /**
     * Returns null if there root has no header or can't find key in header.
     */
    protected JSONObject readHeaderAttr(JSONObject root, String key) {
        if(!root.containsKey("header"))
            return null;
        JSONObject header = (JSONObject) root.get("header");
        return (JSONObject) header.get(key);
    }


    /**
     * Calculates the offset + margin into the Coordinates if they exist.
     */
    protected Point getCoordinates(int x, int y, int xShift, int yShift) {
        return new Point(x+xShift, y+yShift);
    }


    // -------------------------------- game -> save file transformation -----------------------------------------------


    /**
     * Game to Elements (Save).
     */
    @Override @SuppressWarnings("unchecked")
    protected List<JSONObject> translateGame(){
        List<Cell> aliveCells = getAliveCells();
        JSONObject header = new JSONObject();
        JSONObject offset = getOffset(aliveCells);

        List<JSONObject> save_data = super.translateGame();
        JSONObject wrapper = save_data.get(0);

        header.put("offset", offset);
        wrapper.put("header", header);
        return save_data;
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


    public void setMargin(int xMargin, int yMargin){
        if(xMargin < 0 || yMargin < 0)
            throw new RuntimeException("Cannot set margin to a negative value: "+xMargin+" and "+yMargin);
        this.xMargin = xMargin;
        this.yMargin = yMargin;
    }


    @SuppressWarnings("unchecked")
    protected void addToHeader(JSONObject root, String key, JSONObject value){
        JSONObject header = (JSONObject) root.get("header");
        if(header != null)
            header.put(key, value);
    }
}
