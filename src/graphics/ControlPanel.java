package graphics;

import graphics.GUIRunnable;

import javax.swing.*;

public class ControlPanel {

    GUIRunnable game;
    JButton run;
    JButton act;
    JButton stop;

    public ControlPanel(GUIRunnable game){
        this.game = game;
    }
}
