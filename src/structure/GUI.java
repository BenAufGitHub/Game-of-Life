package structure;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Image;

public abstract class GUI extends JFrame implements Output {

    private Game game;
    private Settings settings;

    protected JButton run;
    protected JButton act;
    protected JButton stop;

    private ControlPanel control;
    private GridPanel grid;


    public GUI(int x, int y, Settings settings){
        if(x > 200 || y> 200){
            ErrorHandler.catchError(new DimensionsTooBigException(), 5, false);
        }
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
        grid = new GridPanel(x, y, getSettings(), sl);

        control.add(run);
        control.add(act);
        control.add(stop);

        this.setGame(new EmptyGame(this));
    }


    @Override
    public final void setGame(Game game){
        this.game = game;
        getGridPanel().getSelector().setGame(game);
        afterGameIsSet();
    }


    /**
     * Called after new Game is initialized with this GUI.
     * Can be overridden to add functionality.
     */
    public void afterGameIsSet(){
    }


    public ImageIcon scaleImage(Image image, JLabel label){
        Image scaled = image.getScaledInstance(label.getWidth(), label.getHeight(),  Image.SCALE_DEFAULT);
        return new ImageIcon(scaled);
    }


    /**
     * adds custom Components to ControlPanel (never GridPanel!)
     * the feature can be blocked by overriding this method
     */
    public void addToControlPanel(JComponent jcomponent){
        this.getControlPanel().add(jcomponent);
    }


    /**
     * if a game wants to recreate the specified cell,
     * it can get its Blueprint and request for other Cells to be changes likewise.
     */
    @Override
    public Blueprint getBlueprint(int x, int y){
        JLabel label = getGridPanel().getGrid()[y][x];
        ImageIcon icon = (ImageIcon) label.getIcon();
        if(icon == null){
            return new Blueprint(label.getBackground(), null);
        }
        return new Blueprint(label.getBackground(), icon.getImage());
    }


    public final int gridHeight(){
        return getGridPanel().getGrid().length;
    }

    public final int gridWidth(){
        return getGridPanel().getGrid()[0].length;
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

    protected final GridPanel getGridPanel(){ return grid; }


    /**
     deactivates all buttons that would cause unsafe threading when a specific button is pressed
     */
    protected void deactivateButtons(Clicked button){
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
    protected void buttonsToDefault(){
        run.setEnabled(true);
        act.setEnabled(true);
        stop.setEnabled(false);
    }


    public enum Clicked {
        ACT, RUN, STOP
    }


    protected class ButtonFactory{

        protected final JButton createRunButton(){
            JButton button = new JButton("run");
            button.setFocusable(false);
            button.addActionListener( e -> {
                Thread t = new Thread( () -> {
                    if(game==null) {return;}
                    try {
                        deactivateButtons(Clicked.RUN);
                        game.run();
                        buttonsToDefault();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        ErrorHandler.catchError(GUI.this, ex, 2, true);
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
                    if(game==null) {return;}
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
            button.setEnabled(false);
            button.setFocusable(false);
            button.addActionListener(e -> {
                Thread t = new Thread( () -> {
                    if(game==null) {return;}
                    deactivateButtons(Clicked.STOP);
                    game.stop();
                });
                t.start();
            });
            return button;
        }

    }

    private class DimensionsTooBigException extends Exception{
        public DimensionsTooBigException(){
            super("Either x or y extended the dimension limit of 200");
        }
    }
}
