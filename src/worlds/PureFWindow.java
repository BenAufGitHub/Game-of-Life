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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.HashMap;


/**
 * PureFWindow: FixedWindow, a static Window, you cannot zoom in nor move inside the GridPanel
 */
public class PureFWindow extends GUI {
    private int WIDTH = 1100;
    private int HEIGHT = 800;
    private int maxSqueezeX = 650;
    private int maxSqueezeY = 265;
    private HashMap<Image, ImageIcon> scaledImages = new HashMap<>();


    public PureFWindow(int x, int y, Settings settings) {
        super(x, y, settings);
        JPanel control = getControlPanel();
        JPanel grid = getGridPanel();
        JPanel gridWrapper = new JPanel();

        gridWrapper.setPreferredSize(new Dimension(800,0));
        gridWrapper.setBackground(Color.LIGHT_GRAY);
        gridWrapper.setLayout(new GridBagLayout());

        scaleGrid(x, y);
        scaleComponentsAccordingToGrid(gridWrapper, this);
        gridWrapper.add(grid, new GridBagConstraints());

        this.setLayout(new BorderLayout(10,0));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocation(0, 0);
        this.setTitle("start.structure.Game of Life");

        this.add(control, BorderLayout.EAST);
        this.add(gridWrapper, BorderLayout.WEST);
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
        if(color != null && getSettings().coloursChangeable() && label.getBackground() != color){
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
                ErrorHandler.catchError(this, exception, 4, true);
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


    //-------------------------------- scaling ------------------------------------------------


    /**
     * If Grid has CellsHorizontal != CellsVertical -> minimizes whitespace by shrinking the window and gridWrapper Panel.
     */
    private void scaleComponentsAccordingToGrid(JPanel gridWrapper, GUI gui) {
        Dimension xyDiff = getGridSizeDiff();
        int xDiff = Math.min(xyDiff.width, maxSqueezeX);
        int yDiff = Math.min(xyDiff.height, maxSqueezeY);

        Dimension wrapperSize = gridWrapper.getPreferredSize();
        gridWrapper.setPreferredSize(new Dimension (wrapperSize.width-xDiff, 500));

        WIDTH -= xDiff;
        HEIGHT -= yDiff;
        gui.setSize(WIDTH, HEIGHT);
    }


    /**
     * @return Gives the difference between Cells in x- and in y-direction. Example x:50, y:10 -> Dimension(0, 40)
     */
    private Dimension getGridSizeDiff(){
        Dimension d = getGridPanel().getPreferredSize();
        int diff = d.width-d.height;
        if(diff > 0)
            return new Dimension(0, diff);
        return new Dimension(-diff, 0);
    }


    /** Scales Grid Dimensions so that all inside Cells are squares. */
    private void scaleGrid(int horizontalCells, int verticalCells) {
        Dimension d = getProperGridScale(horizontalCells, verticalCells);
        getGridPanel().setPreferredSize(d);
    }


    /**
     * In order to place Images into Cells aka JLabels of the grid, they here need to be adjusted to the right space.
     */
    private Dimension getProperGridScale(int hCells, int vCells){
        JPanel panel = getGridPanel();
        Dimension d = panel.getPreferredSize();
        int width = d.width;
        int height = d.height;

        if(hCells<vCells){
            width = (int) (((double) width) *((double)hCells)/((double)vCells));
        }
        if(vCells<hCells){
            height = (int) (((double) height) *((double)vCells)/((double)hCells));
        }
        return new Dimension(width, height);
    }

}
