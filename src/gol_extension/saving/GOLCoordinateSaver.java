package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import tools.CoordinateSaver;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * Since I settled on using JSONObjects to save the game, this class is redundant.
 * Anyways, this class can be used if there is a need in the future.
 * This class remains as the quality test for the tools.CoordinateSaver class.
 */
 public class GOLCoordinateSaver extends GOLSaver<Point>{


    public GOLCoordinateSaver(GameOfLife game) {
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
        List<Point> points = new ArrayList<>();
        for(Cell cell : getAliveCells()){
            points.add(new Point(cell.getX(), cell.getY()));
        }
        return points;
    }
}
