import gol_extension.GameOfLife;
import gol_extension.updates.UpdaterRed;
import structure.GUI;
import structure.Settings;
import worlds.ExtendedFixedWindow;

import java.awt.Color;

public class AccessPoint {
    public static void main(String[] args){
        Settings settings = new Settings(Color.GRAY, true, false);
        GUI gui = new ExtendedFixedWindow(40,40, settings);
        GameOfLife game = new GameOfLife(gui, 40, 40);
        game.setUpdater(new UpdaterRed(gui));
        gui.setVisible(true);
    }
}
