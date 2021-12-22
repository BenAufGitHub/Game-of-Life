import gol_extension.GameOfLife;
import gol_extension.updates.UpdaterClean;
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
        game.setUpdater(new UpdaterClean(gui));
        gui.setVisible(true);
    }
}

//TODO merge into dev/ branches
//TODO change dev/branches to border/borderless
//TODO delete some unnecessary Branches
//TODO implement save button
//TODO plot a coupling diagram

//TODO create faster gol versions and compare
//TODO implement suggested most efficient version + compare
