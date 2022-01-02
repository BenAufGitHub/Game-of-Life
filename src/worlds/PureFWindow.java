package worlds;

import structure.Blueprint;
import structure.ErrorHandler;
import structure.GUI;
import structure.Settings;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.util.HashMap;


/**
 * PureFWindow: FixedWindow, a static Window, you cannot zoom in nor move inside the GridPanel
 */
public class PureFWindow extends GUI {
    private final int WIDTH = 1100;
    private final int HEIGHT = 800;
    private HashMap<Image, ImageIcon> scaledImages = new HashMap<>();


    public PureFWindow(int x, int y, Settings settings) {
        super(x, y, settings);

        JPanel control = getControlPanel();
        JPanel grid = getGridPanel();
        JPanel gridWrapper = new JPanel();

        this.setLayout(new BorderLayout(10,0));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setBounds(0,0, WIDTH,  HEIGHT);
        this.setTitle("start.structure.Game of Life");

        gridWrapper.setPreferredSize(new Dimension(800,0));
        gridWrapper.setBackground(Color.LIGHT_GRAY);
        gridWrapper.setLayout(new FlowLayout());

        Dimension d = getProperGridScale(x,y);
        grid.setSize(d.width, d.height);
        gridWrapper.add(grid);

        this.add(control, BorderLayout.EAST);
        this.add(gridWrapper, BorderLayout.WEST);
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
            super("The coordinates ("+x+"/"+y+") are not in the bounds of the extension.Grid!");
        }
    }

}