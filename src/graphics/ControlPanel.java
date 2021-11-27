package graphics;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    GUIRunnable game;
    JButton run;
    JButton act;
    JButton stop;

    public ControlPanel(GUIRunnable game){
        this.game = game;
        setLayout(null);

        run = new JButton("run");
        act = new JButton("act");
        stop = new JButton("stop");

        run.setBounds(100, 50, 100, 60);
        act.setBounds(100, 150, 100, 60);
        stop.setBounds(100, 250, 100, 60);

        setRunAction();
        act.addActionListener(e -> game.act());
        stop.addActionListener(e -> game.stop());

        this.add(run);
        this.add(act);
        this.add(stop);

        this.setBackground(Color.RED);
    }

    private void setRunAction() {
        run.addActionListener( e -> {
            try {
                game.run();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                ErrorHandler.catchError((GUI) this.getParent(), ex, 2);
            }
        } );
    }
}
