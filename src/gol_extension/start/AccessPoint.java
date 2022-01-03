package gol_extension.start;

import gol_extension.updates.Updater;
import gol_extension.updates.UpdaterClean;
import gol_extension.updates.UpdaterRed;
import structure.Selector;
import structure.Settings;
import tools.SaveManager;
import worlds.HeaderFWindow;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.io.IOException;

public class AccessPoint {

    public static void main(String[] args) throws IOException {

        Settings settings = new Settings(Color.GRAY, true);
        WindowGOL window = new WindowGOL(100, 100, settings);
        ExtendedGOL gol = new ExtendedGOL(window, 100, 100, window);

        Updater colouring = new UpdaterClean(window);
        String save_name = "save.txt";

        gol.setUpdater(colouring);                                        //determines colouring

        if(save_name != null)
            new Selector(gol).selectAll(SaveManager.get(save_name));         // loads a save

        window.setVisible(true);
    }
}


//TODO
//  make method to add buttons
//  set Speed for Subclasses of FWindow
//  make other games and compare time + optimal solution
//  implement save button
//  implement error output
//  update preselect class
//  plot the references onto paper