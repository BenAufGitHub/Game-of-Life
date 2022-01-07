package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import org.json.simple.JSONObject;
import tools.CoordinateSaver;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GOLCoordSaver extends GOLSaver<Point>{


    public GOLCoordSaver(GameOfLife game) {
        super(game, new CoordinateSaver());
    }


    @Override
    protected void translateElements(List<Point> list){
        for(Point p: list){
            if(p != null)
                getGame().clicked(p.x, p.y);
        }
    }

    
    @Override
    protected List<Point> translateGame() {
        List<Point> points = new ArrayList();
        for(Cell cell : getAliveCells()){
            points.add(new Point(cell.getX(), cell.getY()));
        }
        return points;
    }
}
