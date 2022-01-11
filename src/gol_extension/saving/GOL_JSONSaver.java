package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tools.JSONSaver;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * This class can save the GameOfLife Cells as a JSON object.
 * Although with the extension of GOLSaver, the List<JSONObject> offers many slots to save data,
 * for now I am using only the first slot (list.get(0)) and putting all JSONObjects inside that, because else I would
 * violate the List<Element> structure, where elements would differ in their purpose.
 * The other slots could be needed if one would want to save multiple structures in one save-file.
 * Structure:
 *      List:
 *          [0]: JSONObject:
 *                  {
 *                      contents: {coordinates0, coordinates1, ... , coordinatesX}
 *                  }
 */
public class GOL_JSONSaver extends GOLSaver<JSONObject>{

    public GOL_JSONSaver(GameOfLife game) {
        super(game, new JSONSaver());
    }


    @Override
    protected void translateElements(List<JSONObject> objects){
        for(JSONObject json : retrieveContentsFromRootObject(objects.get(0))){
            Point coordinates = getJSONCoordinates(json);
            getGame().clicked(coordinates.x, coordinates.y);
        }
    }


    @Override
    protected List<JSONObject> translateGame(){
        List<JSONObject> list = new ArrayList<>();
        JSONObject wrapper = new JSONObject();
        JSONArray objects = new JSONArray();

        for(Cell c: getAliveCells())
            objects.add(toJSON(c.getX(), c.getY()));
        wrapper.put("contents", objects);
        list.add(wrapper);
        return list;
    }


    @SuppressWarnings("unchecked")
    protected JSONObject toJSON(int x, int y){
        JSONObject js = new JSONObject();
        js.put("x", x);
        js.put("y", y);
        return js;
    }


    protected Point getJSONCoordinates(JSONObject obj){
        long x = (long) obj.get("x");
        long y = (long) obj.get("y");
        return new Point((int) x, (int) y);
    }


    public List<JSONObject> retrieveContentsFromRootObject(JSONObject root){
        List<JSONObject> list = new ArrayList<>();
        JSONArray arr = (JSONArray) root.get("contents");
        for(Object obj : arr)
            list.add((JSONObject) obj);
        return list;
    }
}
