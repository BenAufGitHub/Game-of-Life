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
        act.setFocusable(false);
        run.setFocusable(false);

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
                    buttonsToDefault();
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
                buttonsToDefault();
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


    /*
    deactivates all buttons that would cause unsafe threading when a specific button is pressed
     */
    private void deactivateButtons(Clicked button){
        run.setEnabled(false);
        act.setEnabled(false);

        if(button == Clicked.RUN){
            stop.setEnabled(true);
        } else
            stop.setEnabled(false);
    }


    /*
    buttons into default position: run+act enabled, stop disabled
     */
    private void buttonsToDefault(){
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
