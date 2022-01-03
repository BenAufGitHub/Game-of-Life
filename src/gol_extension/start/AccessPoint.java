package gol_extension.start;

import gol_extension.updates.Updater;
import gol_extension.updates.UpdaterClean;
import gol_extension.updates.UpdaterRed;
import structure.Selector;
import structure.Settings;
import tools.SaveManager;

import java.awt.Color;
import java.io.IOException;

public class AccessPoint {

    public static void main(String[] args) throws IOException {
        // enter the name of a save in the resources folder
        final String save_name = null;

        // Set up Settings, Window and Game.
        Settings settings = new Settings(Color.GRAY, true);
        WindowGOL window = new WindowGOL(100, 100, settings);
        ExtendedGOL gol = new ExtendedGOL(window, 100, 100, window);

        // Choose UpdaterClean or UpdaterRed or a custom Updater.
        Updater colouring = new UpdaterRed(window);

        // determines colouring
        gol.setUpdater(colouring);

        // loads a save
        if(save_name != null)
            new Selector(gol).selectAll(SaveManager.get(save_name));

        window.setVisible(true);
    }
}