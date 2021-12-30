package structure;

import java.awt.Point;
import java.util.HashSet;

public class Selector {
    private Game game;

    public Selector(Game game){
        this.game = game;

    }

    public void select(int x, int y){
        game.clicked(x, y);
    }

    public void selectAll(Point[] points){
        for(Point p : points){
            game.clicked(p.x, p.y);
        }
    }

    public void setGame(Game game){
        this.game = game;
    }


}
