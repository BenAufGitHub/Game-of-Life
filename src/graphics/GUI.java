package graphics;

import components.GridChangeListener;
import components.Action;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;


public class GUI extends JFrame implements GridChangeListener {

    public final int WIDTH = 1300;
    public final int HEIGHT = 800;

    private JPanel gridPanel;
    private JPanel controlPanel;
    private Dimension gridDimension;
    private BorderLayout layout = null;
    private GUIRunnable game;

    public GUI(Dimension grid, GUIRunnable game){
        this.gridDimension = grid;
        this.game = game;

        setFrameSettings();
        this.gridPanel = createGridPanel();
        this.controlPanel = createControlPanel(game);

        this.add(gridPanel, BorderLayout.WEST);
        this.add(controlPanel, BorderLayout.EAST);
        setVisible(true);
    }


    private JPanel createControlPanel(GUIRunnable game){
        JPanel jp = new ControlPanel(game);
        jp.setPreferredSize(new Dimension(300,0));
        return jp;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel();

        panel.setBackground(Color.gray);
        panel.setPreferredSize(new Dimension(getGridWidth(),0));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

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

    }
}
