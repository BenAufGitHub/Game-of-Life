package graphics;

import components.GridChangeListener;
import components.Action;

import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame implements GridChangeListener {

    public final int WIDTH = 1100;
    public final int HEIGHT = 800;

    private JPanel gridPanel;
    private JPanel controlPanel;
    private Dimension gridDimension;
    private BorderLayout layout = null;
    private JLabel[][] grid;
    private GUIRunnable game;
    private GUISettings settings;

    public GUI(Dimension grid, GUIRunnable game){
        this.settings = new GUISettings(true); //Standard
        this.gridDimension = grid;
        this.game = game;
        this.grid = new JLabel[grid.height][grid.width];

        setFrameSettings();
        this.gridPanel = createGridPanel();
        this.controlPanel = createControlPanel(game);

        this.add(gridPanel, BorderLayout.WEST);
        this.add(controlPanel, BorderLayout.EAST);
        setVisible(true);
    }

    public void setSettings(GUISettings settings) {
        this.settings = settings;
    }

    private JPanel createControlPanel(GUIRunnable game){
        JPanel jp = new ControlPanel(game, this);
        jp.setPreferredSize(new Dimension(300,0));
        return jp;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(getGridWidth(),0));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setLayout(new GridLayout(gridDimension.width, gridDimension.height));

        for(int y=0; y< gridDimension.height; y++){
            for(int x=0; x< gridDimension.width; x++){
                JLabel label = new JLabel();
                label.setBackground(Color.GRAY);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                addToGrid(x, y, label);
                panel.add(label);
            }
        }

        return panel;
    }

    private void setFrameSettings() {
        this.layout = new BorderLayout(10,0); //TODO maybe no this.layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(this.layout);
        setBounds(0,0, WIDTH, HEIGHT);
        setTitle("start.Game of Life");
    }

    public Dimension getGridDimension(){ return gridDimension; }

    public int getGridWidth(){ return getWidth() - 300; }


    @Override
    public void visualizeGridChange(int x, int y, Action a) {
        JLabel[][] grid = getGrid();
        JLabel label = grid[y][x];

        if(a == Action.LIVE){
            Image image =  new ImageIcon("resources//black_circle.png").getImage();
            Image scaled = image.getScaledInstance(label.getWidth(), label.getHeight(),  Image.SCALE_DEFAULT);
            label.setIcon(new ImageIcon(scaled));
        }
        if(a == Action.DIE){
            label.setIcon(null);
        }
        if(a == Action.COLOR && settings.isTrackIndicated()){
            label.setBackground(Color.RED);
        }
        if(a == Action.PLAIN){
            label.setBackground(Color.GRAY);
        }
    }


    public JLabel[][] getGrid(){
        return grid;
    }

    private void addToGrid(int x, int y, JLabel label){
        JLabel[][] grid = getGrid();
        grid[y][x] = label;
    }
}
