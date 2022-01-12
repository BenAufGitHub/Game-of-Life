package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONObject;

import java.awt.Point;
import java.util.List;


/**
 * positions all loaded structures centered into the Grid.
 * Can read saves from superclass.
 * Reading superclass description recommended.
 * Structure:
 *       List:
 *           [0]: JSONObject:
 *                   {
 *                       "header": {"offset": coordinates, "max_offset": coordinates}
 *                       "contents": {coordinates0, coordinates1, ... , coordinatesX
 *                   }
 **/
public class CentralSaver extends OffsetSaver{

    private int minMarginX = STANDARD_X_MARGIN;
    private int minMarginY = STANDARD_Y_MARGIN;


    public CentralSaver(GameOfLife game) {
        super(game);
    }


    //------------------------------- save file -> game -----------------------------------------------------


    /**
     * Elements to game (load).
     */
    @Override
    protected void translateElements(List<JSONObject> objects){
        Point shiftToCenter = calcShift(objects.get(0));
        for(JSONObject obj : retrieveContentsFromRootObject(objects.get(0))){
            Point coordinates = getJSONCoordinates(obj);
            Point point = getCoordinates(coordinates.x, coordinates.y, shiftToCenter.x, shiftToCenter.y);
            getGame().clicked(point.x, point.y);
        }
    }


    /**
     * If root has a maxOffset Key, this calcs relative offset to the center.
     * If not but has a minOffset Key, super method calcs relative offset to top-left corner.
     * else return no offset (new Point(0, 0))
     */
    @Override
    protected Point calcShift(JSONObject root) {
        JSONObject offset = readHeaderAttr(root, "offset");
        JSONObject maxOffset = readHeaderAttr(root, "max_offset");
        if(maxOffset == null)
            return super.calcShift(root);

        // minPoint and maxPoint of the structure
        Point min = getJSONCoordinates(offset);
        Point max = getJSONCoordinates(maxOffset);

        int width = 1 + max.x - min.x;
        int height = 1 + max.y - min.y;

        // Margin from top/left corner in order to center
        int xMargin = (getGame().getWidth() - width) / 2;
        int yMargin = (getGame().getHeight() - height) / 2;

        // relative shift
        int xShift = Math.max(xMargin, minMarginX) - min.x;
        int yShift = Math.max(yMargin, minMarginY) - min.y;
        return new Point(xShift, yShift);
    }


    //-------------------------------- game -> save file --------------------------------


    /**
     * Game to Elements (Save).
     */
    @Override
    protected List<JSONObject> translateGame(){
        List<JSONObject> saveList = super.translateGame();
        JSONObject root = saveList.get(0);
        JSONObject maxOffset = getMaxOffset(getAliveCells());
        addToHeader(root, "max_offset", maxOffset);
        return saveList;
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


    /**
     * Set how from top-left corner the loaded-structure should be at least, if game limitations make the auto-margin to small.
     */
    public void setMinimumRelativeMargin(int x, int y){
        minMarginX = x;
        minMarginY = y;
    }
}
