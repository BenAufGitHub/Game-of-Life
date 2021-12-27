import gol_extension.GameOfLife;
import gol_extension.updates.UpdaterClean;
import gol_extension.updates.UpdaterRed;
import structure.Settings;
import worlds.ExtendedFixedWindow;

import java.awt.Color;

public class AccessPoint {

    public static void main(String[] args){
        Settings settings = new Settings(Color.GRAY, true);
        ExtendedFixedWindow window = new ExtendedFixedWindow(100, 100, settings);
        GameOfLife gol = new GameOfLife(window, 100, 100);
        gol.setUpdater(new UpdaterRed(window));

        window.setVisible(true);
    }
}


//TODO
//make other games and compare time + optimal solution
//implement counter
//implement save button
//update preselect class
//optimize imports
//optimize documentation
//plot the references onto paper