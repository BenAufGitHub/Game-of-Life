import gol_extension.CountingGOL;
import gol_extension.GameOfLife;
import gol_extension.updates.UpdaterClean;
import gol_extension.updates.UpdaterRed;
import structure.Settings;
import worlds.ExtendedFixedWindow;
import worlds.HeaderFWindow;

import javax.swing.*;
import java.awt.*;

public class AccessPoint {

    public static void main(String[] args){
        Settings settings = new Settings(Color.GRAY, true);
        HeaderFWindow window = new HeaderFWindow(100, 100, settings);
        CountingGOL gol = new CountingGOL(window, 100, 100);
        gol.setUpdater(new UpdaterRed(window));

        gol.setCountPrinter(window);
        window.setHeader("The Game Of Life / Generation: 1");
        window.setHeaderTextAlignment(SwingConstants.CENTER);
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