package gol_extension.start;

import structure.Settings;
import worlds.HeaderFWindow;

import javax.swing.SwingConstants;

public class WindowGOL extends HeaderFWindow {


    public WindowGOL(int x, int y, Settings settings) {
        super(x, y, settings);
        setHeaderTextAlignment(SwingConstants.CENTER);
    }
}
