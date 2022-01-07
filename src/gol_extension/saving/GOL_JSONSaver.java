package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONObject;
import tools.JSONSaver;

import java.util.ArrayList;
import java.util.List;


/**
 * This class can save the GameOfLife Cells as a JSON object.
 * Since I settled on using awt.Point to save the game, this class is redundant.
 * Anyways, this class can be used if there is a need in the future.
 * This class remains as the quality test for the tools.JSONSaver class.
 */
public class GOL_JSONSaver extends GOLSaver<JSONObject>{

    public GOL_JSONSaver(GameOfLife game) {
        super(game, new JSONSaver());
    }


    @Override
    protected void translateElements(List<JSONObject> objects){
        for(JSONObject obj : objects){
            long x = (long) obj.get("x");
            long y = (long) obj.get("y");
            getGame().clicked((int) x, (int) y);
        }
    }


    @Override
    protected List<JSONObject> translateGame(){
        ArrayList<JSONObject> objects = new ArrayList<>();
        for(Cell c: getAliveCells())
            objects.add(toJSON(c.getX(), c.getY()));
        return objects;
    }


    protected JSONObject toJSON(int x, int y){
        JSONObject js = new JSONObject();
        js.put("x", x);
        js.put("y", y);
        return js;
    }
}
