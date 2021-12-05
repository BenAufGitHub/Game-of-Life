import structure.Blueprint;
import structure.GUI;
import structure.Settings;

import javax.swing.*;
import java.awt.*;

public class FixedWindow extends GUI {
    private final int WIDTH = 1100;
    private final int HEIGHT = 800;

    public static void main(String[] args){
        new FixedWindow(10,5, new Settings(Color.GRAY, true));
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

        Dimension d = getProperGridScale(x,y);
        gridWrapper.setPreferredSize(grid.getPreferredSize());
        System.out.println(getGridPanel().getLayout() + " " +d.height);
        grid.setBounds(0,0, d.width, d.height);
        gridWrapper.setLayout(null);
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

    @Override
    public void showAction(int x, int y, Action action) {

    }

    @Override
    public void showAction(int x, int y, Blueprint blueprint) {

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
}
