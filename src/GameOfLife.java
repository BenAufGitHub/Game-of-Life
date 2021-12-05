import structure.Blueprint;
import structure.Game;
import structure.Output;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Image;

public class GameOfLife extends Game {
    private final static Image circle = new ImageIcon("resources//black_circle.png").getImage();
    private final static Color tracked = Color.RED;
    private final static Blueprint live = new Blueprint(tracked, circle);
    private final static Blueprint clear = new Blueprint(Color.GRAY, null);

    public GameOfLife(Output op) {
        super(op);
    }

    @Override
    public void reset() {

    }

    @Override
    public void clicked(int x, int y) {
        if(running())
            return;
        //TODO
    }

    @Override
    protected void act() {

    }
}
