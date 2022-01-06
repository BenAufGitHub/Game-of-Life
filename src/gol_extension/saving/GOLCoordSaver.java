package gol_extension.saving;

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
        for(int x=0; x< getGame().getWidth(); x++){
            for(int y=0; y< getGame().getHeight(); y++){
                if(cellAlive(x,y)){
                    points.add(new Point(x,y));
                }
            }
        }
        return points;
    }


    private boolean cellAlive(int x, int y){
        return getGame().getCellTracker().getGrid().getCell(x,y).isAlive();
    }
}
