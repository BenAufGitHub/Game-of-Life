package structure;

import structure.Game;

import java.awt.*;
import java.util.HashSet;

public class Selector {
    private HashSet<Point> selection= new HashSet<>();
    private Game game;

    public Selector(Game game){
        this.game = game;

    }

    public void select(int x, int y){
        game.clicked(x, y);
    }

    public void preselect(){
        for(Point p : selection){
            game.clicked(p.x, p.y);
        }
    }

    public void setGame(Game game){
        this.game = game;
    }

    public void setPreselection(HashSet<Point> selection){
        this.selection = selection;
    }

    public void clearSelection(){
        this.selection = new HashSet();
    }


}
