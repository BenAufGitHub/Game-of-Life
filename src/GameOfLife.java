import structure.Blueprint;
import structure.Game;
import structure.Output;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;

public class GameOfLife extends Game {
    private static Image circle = new ImageIcon("resources//black_circle.png").getImage();
    private static Color tracked = Color.RED;
    private static Blueprint live = new Blueprint(tracked, circle);

    public GameOfLife(Output op) {
        super(op);
    }

    @Override
    public void reset() {

    }

    @Override
    public void clicked(int x, int y) {
        getOutput().showAction(x,y, live);
    }

    @Override
    protected void act() {

    }
}
