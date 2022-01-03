import gol_extension.CountingGOL;
import gol_extension.GameOfLife;
import gol_extension.SaveExtension;
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
        HeaderFWindow window = new HeaderFWindow(100, 100, settings);

        CountingGOL gol = new CountingGOL(window, 100, 100);
        gol.enableSaving(70, 700, 170, 700); //adds buttons to window
        gol.setUpdater(new UpdaterRed(window));                                        //determines colouring
        gol.setCountPrinter(window);                                                   // where Count is shown
        new Selector(gol).selectAll(SaveManager.get("save.txt"));                          // loads a save

        window.setHeader("The Game Of Life / Generation: 1");
        window.setHeaderTextAlignment(SwingConstants.CENTER);
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