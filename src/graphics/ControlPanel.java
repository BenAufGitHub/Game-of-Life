package graphics;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    enum Clicked{
        RUN, ACT, STOP
    }

    GUIRunnable game;
    GUI gui;
    JButton run;
    JButton act;
    JButton stop;

    public ControlPanel(GUIRunnable game, GUI gui){
        this.game = game;
        this.gui = gui;
        setLayout(null);

        run = new JButton("run");
        act = new JButton("act");
        stop = new JButton("stop");

        run.setBounds(100, 50, 100, 60);
        act.setBounds(100, 150, 100, 60);
        stop.setBounds(100, 250, 100, 60);

        prepareActionListeners();

        stop.setEnabled(false);
        stop.setFocusable(false);

        this.add(run);
        this.add(act);
        this.add(stop);

        this.setBackground(Color.RED);
    }

    /*
    (own method because expression was too messy for Instructor)
    Exception gets passed to ErrorHandler
     */
    private void prepareActionListeners() {
        run.addActionListener( e -> {
            Thread t = new Thread( () -> {
                try {
                    deactivateButtons(Clicked.RUN);
                    game.run();
                    activateButtons();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ErrorHandler.catchError((GUI) this.getParent(), ex, 2);
                }
            });
            t.start();
        });

        act.addActionListener(e -> {
            Thread t = new Thread( () -> {
                deactivateButtons(Clicked.ACT);
                game.act();
                activateButtons();
            });
            t.start();
        });

        stop.addActionListener(e -> {
            Thread t = new Thread( () -> {
                deactivateButtons(Clicked.STOP);
                game.stop();
            });
            t.start();
        });
    }

    private void autoToggleButtonStatus(Clicked button){ //TODO
        run.setEnabled(false);
        act.setEnabled(false);

        if(button == Clicked.ACT || button == Clicked.STOP){
            stop.setEnabled(false);
        }
        if(button == Clicked.RUN){
            stop.setEnabled(true);
        }
        awaitProcessFinish();
        run.setEnabled(true);
        act.setEnabled(true);
        stop.setEnabled(false);
    }

    public void deactivateButtons(Clicked button){
        run.setEnabled(false);
        act.setEnabled(false);

        if(button == Clicked.RUN){
            stop.setEnabled(true);
        } else
            stop.setEnabled(false);
    }

    public void activateButtons(){
        run.setEnabled(true);
        act.setEnabled(true);
        stop.setEnabled(false);
    }

    private void awaitProcessFinish(){
        synchronized (game.awaiter){
            try{
                game.awaiter.wait();
                System.out.println("Worked");
            } catch(InterruptedException ex){
                ErrorHandler.catchError((GUI) this.getParent(), ex, 3);
            }
        }
    }
}
