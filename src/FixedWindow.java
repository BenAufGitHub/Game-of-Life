import structure.*;

import javax.swing.*;
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

    public static void main(String[] args){
        GUI gui = new FixedWindow(10,10, new Settings(Color.GRAY, true));
        new GameOfLife(gui);
    }

    public FixedWindow(int x, int y, Settings settings) {
        super(x, y, settings);

        JPanel control = getControlPanel();
        JPanel grid = getGridPanel();
        JPanel gridWrapper = new JPanel();

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

        this.add(control, BorderLayout.EAST);
        this.add(gridWrapper, BorderLayout.WEST);

        this.setVisible(true);
    }


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
     * @param x
     * @param y
     * @param action (STANDARD, BORDERLESS, BORDER, OPAQUE, NON_OPAQUE)
     */
    @Override
    public void showAction(int x, int y, Action action) {
        JLabel[][] grid = getGridPanel().getGrid();

        JLabel label = getLabel(x, y, grid);

        if(action == Action.STANDARD){
            Color color = getSettings().getStandardGridColor();
            label.setBackground(color);
            label.setIcon(null);
        }
        if(action == Action.BORDERLESS){
            label.setBorder(null);
        }
        if(action == Action.BORDER){
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
        if(action == Action.OPAQUE){
            label.setOpaque(true);
        }
        if(action == Action.NON_OPAQUE)
            label.setOpaque(false);
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
     * @param x
     * @param y
     * @param grid
     * @return
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


    public static class CoordinatesNotInBoundsException extends Exception{
        public CoordinatesNotInBoundsException(int x, int y){
            super("The coordinates ("+x+"/"+y+") are not in the bounds of the Grid!");
        }
    }
}
