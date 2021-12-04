import javax.swing.*;
import java.awt.*;

public abstract class GUI extends JFrame implements Output{

    private Game game;
    private Settings settings;

    protected JButton run;
    protected JButton act;
    protected JButton stop;

    private ControlPanel control;
    private GridPanel grid;


    public GUI(int x, int y, Settings settings){
        setSettings(settings);
        ButtonFactory factory = new ButtonFactory();

        this.run = factory.createRunButton();
        this.act = factory.createActButton();
        this.stop = factory.createStopButton();

        run.setBounds(100, 50, 100, 60);
        act.setBounds(100, 150, 100, 60);
        stop.setBounds(100, 250, 100, 60);

        Selector sl = new Selector(getGame());
        control = new ControlPanel();
        grid = new GridPanel(x, y, settings.getStandardGridColor(), sl);

        control.add(run);
        control.add(act);
        control.add(stop);
    }


    @Override
    public final void setGame(Game game){
        this.game = game;
    }

    public final Game getGame(){ return game; }

    @Override
    public final void setSettings(Settings settings) {
        this.settings = settings;
    }

    public final Settings getSettings(){ return settings; }

    protected final ControlPanel getControlPanel(){
        return control;
    }

    protected final GridPanel getGrid(){ return grid; }





    protected class ButtonFactory{
        enum Clicked {
            ACT, RUN, STOP
        }

        protected final JButton createRunButton(){
            JButton button = new JButton("run");
            button.setFocusable(false);
            button.addActionListener( e -> {
                Thread t = new Thread( () -> {
                    try {
                        deactivateButtons(Clicked.RUN);
                        game.run();
                        buttonsToDefault();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ErrorHandler.catchError(GUI.this, ex, 2);
                    }
                });
                t.start();
            });
            return button;
        }


        protected final JButton createActButton(){
            JButton button = new JButton("act");
            button.setFocusable(false);
            button.addActionListener(e -> {
                Thread t = new Thread( () -> {
                    deactivateButtons(Clicked.ACT);
                    game.requestAct();
                    buttonsToDefault();
                });
                t.start();
            });
            return button;
        }


        protected final JButton createStopButton(){
            JButton button = new JButton("stop");
            stop.setEnabled(false);
            button.setFocusable(false);
            button.addActionListener(e -> {
                Thread t = new Thread( () -> {
                    deactivateButtons(Clicked.STOP);
                    game.stop();
                });
                t.start();
            });
            return button;
        }


        /**
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


        /**
        buttons into default position: run+act enabled, stop disabled
         */
        private void buttonsToDefault(){
            run.setEnabled(true);
            act.setEnabled(true);
            stop.setEnabled(false);
        }
    }
}
