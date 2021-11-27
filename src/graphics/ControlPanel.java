package graphics;

import javax.swing.JButton;

public class ControlPanel {

    GUIRunnable game;
    JButton run;
    JButton act;
    JButton stop;

    public ControlPanel(GUIRunnable game){
        this.game = game;
    }
}
