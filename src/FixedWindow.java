import structure.Blueprint;
import structure.ErrorHandler;
import structure.GUI;
import structure.Game;
import structure.Settings;
import structure.TimeSpanException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.HashMap;

public class FixedWindow extends GUI {
    private final int WIDTH = 1100;
    private final int HEIGHT = 800;
    private HashMap<Image, ImageIcon> scaledImages = new HashMap<>();
    private JButton clear = new JButton("clear");

    public static void main(String[] args){
        Settings settings = new Settings(Color.GRAY, true, false);
        GUI gui = new FixedWindow(40,40, settings);
        Game game = new GameOfLife(gui, 40, 40);
    }

    public FixedWindow(int x, int y, Settings settings) {
        super(x, y, settings);

        JPanel control = getControlPanel();
        JPanel grid = getGridPanel();
        JPanel gridWrapper = new JPanel();
        JButton choiceButton = Factory.createSpeedButton(this);

        this.setLayout(new BorderLayout(10,0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(0,0, WIDTH,  HEIGHT);
        setTitle("start.structure.Game of Life");

        gridWrapper.setPreferredSize(new Dimension(800,0));
        gridWrapper.setBackground(Color.LIGHT_GRAY);
        gridWrapper.setLayout(new FlowLayout());

        Dimension d = getProperGridScale(x,y);
        grid.setSize(d.width, d.height);
        gridWrapper.add(grid);

        choiceButton.setBounds(100, 350, 100, 30);
        choiceButton.setFocusable(false);

        clear.setBounds(130, 400, 70, 30);
        clear.setFocusable(false);
        clear.addActionListener( e -> {
            if(getGame().running())
                return;
            getGame().reset();
            this.clear();
        });
        control.add(clear);
        control.add(choiceButton);

        this.add(control, BorderLayout.EAST);
        this.add(gridWrapper, BorderLayout.WEST);

        this.setVisible(true);
    }


    /**
     * in order to place Images into Cells aka JLabels of the grid, they here need to be adjusted to the right space
     * @param x
     * @param y
     * @return
     */
    private Dimension getProperGridScale(int x, int y){
        JPanel panel = getGridPanel();
        Dimension d = panel.getPreferredSize();

        int width = d.width;
        int height = d.height;

        if(x<y){
            width = (int) (((double) width) *((double)x)/((double)y));
        }
        if(y<x){
            height = (int) (((double) height) *((double)y)/((double)x));
        }
        return new Dimension(width, height);
    }


    /**
     * supports: STANDARD, BORDERLESS, BORDER
     * does not support: SELECTED, UNSELECTED,
     * @param action (STANDARD, BORDERLESS, BORDER, OPAQUE, NON_OPAQUE)
     */
    @Override
    public void showAction(int x, int y, Action action) {
        JLabel[][] grid = getGridPanel().getGrid();

        JLabel label = getLabel(x, y, grid);

        switch(action){
            case STANDARD -> {
                Color color = getSettings().getStandardGridColor();
                label.setBackground(color);
                label.setIcon(null);
            }
            case BORDERLESS ->  label.setBorder(null);
            case BORDER ->      label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            case OPAQUE ->      label.setOpaque(true);
            case NON_OPAQUE ->  label.setOpaque(false);

        }
    }


    @Override
    public void showAction(int x, int y, Blueprint blueprint) {
        JLabel[][] grid = getGridPanel().getGrid();

        JLabel label = getLabel(x, y, grid);

        Color color = blueprint.color;
        if(color != null && getSettings().coloursChangable() && label.getBackground() != color){
            label.setBackground(color);
        }
        if(blueprint.iconChange){
            if(blueprint.icon == null){
                label.setIcon(null);
                return;
            }
            if(scaledImages.containsKey(blueprint.icon)){
                label.setIcon(scaledImages.get(blueprint.icon));
                return;
            }
            ImageIcon scaled = scaleImage(blueprint.icon, label);
            scaledImages.put(blueprint.icon, scaled);
            label.setIcon(scaled);
        }
    }


    /**
     * private method in support for showAction, readability purposes, separated error handling
     * if out of bounds, error gets redirected to ErrorHandler
     */
    private JLabel getLabel(int x, int y, JLabel[][] grid){
        JLabel label = null;
        try{
            label = grid[y][x];
        } catch( IndexOutOfBoundsException e){
            try{ throw new CoordinatesNotInBoundsException(x,y); }
            catch(Exception exception){
                ErrorHandler.catchError(this, exception, 4);
            }
        }
        return label;
    }


    @Override
    public void clear() {
        Color color = getSettings().getStandardGridColor();
        JLabel[][] grid = getGridPanel().getGrid();
        for(JLabel[] row : grid){
            for(JLabel label : row){
                label.setIcon(null);
                label.setBackground(color);
            }
        }
    }

    @Override
    public void refresh() {
        revalidate();
    }


    //---------------------------- nested classes --------------------------------------------


    public static class CoordinatesNotInBoundsException extends Exception{
        public CoordinatesNotInBoundsException(int x, int y){
            super("The coordinates ("+x+"/"+y+") are not in the bounds of the Grid!");
        }
    }


    /**
     * pass a String array: by every click, the button changes text to next item + performs action for it
     * params:
     * choices: whatever gets put as an option for the button
     * listener: (func. interface: ChoiceButton.ChoiceListener)
     *      '-> when clicked, selected choice will be param for listener.perform(String)
     */
    static class ChoiceButton extends JButton{


        /**
         * can be applied when multiple when a ChoiceButton/program can have multiple,
         * but not simultaneous, states that can be switched.
         */
        private interface ChoiceListener{
            /**
             * should ideally be called only from choice listener
             * @param s -> the state the program/ChoiceButton is being changed to
             */
            void perform(String s);
        }


        ChoiceListener listener;
        private String[] choices;
        private int choice;


        public ChoiceButton(String[] choices, ChoiceListener listener){
            String[] empty = {"---"};
            this.choices = (choices != null) ? choices : empty;
            this.listener = listener;
            this.choice = 0;

            this.addActionListener(e -> next());

            this.setText(this.choices[0]);
        }


        public void next(){
            if(choices.length < 2)
                return;
            if((++choice)==choices.length){
                choice = 0;
            }
            setText(choices[choice]);
            listener.perform(choices[choice]);
        }
    }


    class Factory {

        /**
         * on click - changes behaviour from slow -> normal -> fast game.
         * creates Specific Button, that changes game breaks from 1000 ms -> 550 ms -> 100 ms -> 1000 ms and so on ...
         * @param window
         * @return
         */
        public static JButton createSpeedButton(GUI window){
            String[] choices = {"Slow", "Normal", "Fast"};
            return new FixedWindow.ChoiceButton(choices, text -> {
                Game game = window.getGame();
                try {
                    switch (text) {
                        case "Normal", "Fast" -> game.setTimeoutLength(game.getTimeoutLength() - 450);
                        case "Slow" -> game.setTimeoutLength(game.getTimeoutLength() + 900);
                    }
                } catch(TimeSpanException e) {
                    e.printStackTrace();
                }
            });
        }


    }
}
