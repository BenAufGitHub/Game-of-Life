package pong_extension;

import structure.Blueprint;
import structure.Settings;
import worlds.HeaderFWindow;

import javax.swing.ImageIcon;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MyWindow extends HeaderFWindow {

    public static final Blueprint CIRCLE = new Blueprint(null, new ImageIcon("resources/black_circle.png").getImage());
    public static final Blueprint BLACK = new Blueprint(Color.BLACK);

    private Set<Point> color = new HashSet<>();
    private Set<Point> toBeDeleted = new HashSet<>();


    public MyWindow(int x, int y, Settings settings) {
        super(x, y, settings);
    }


    private void discardLastGen(){
        for(Point p : toBeDeleted)
            showAction(p.x, p.y, Action.STANDARD);
    }

    public void drawNewGen(){
        discardLastGen();
        for(Point p : color)
            showAction(p.x, p.y, BLACK);
        toBeDeleted = color;
        color = new HashSet<>();
    }


    public void showCircle(int x, int y, int thickness){
        for(int i=-thickness; i<thickness+1; i++){
            for(int j=-thickness; j<thickness+1; j++){
                if(isInBounds(x+i, y+j)){
                    toBeDeleted.remove(new Point(x+i, y+j));
                    color.add(new Point(x+i, y+j));
                }
            }
        }
    }

    public boolean isInBounds(int x, int y){
        if(x<0 || y<0)
            return false;
        return x < gridWidth() && y < gridHeight();
    }
}
