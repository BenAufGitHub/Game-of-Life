import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame implements GridChangeListener{

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
        this.gridPanel = initGridPanel();
        this.controlPanel = initControlPanel();

        this.add(gridPanel, BorderLayout.WEST);
        this.add(controlPanel, BorderLayout.EAST);
        setVisible(true);
    }

    private JPanel initControlPanel() {
        return new JPanel();
    }

    private JPanel initGridPanel() {
        return new JPanel();
    }

    private void setFrameSettings() {
        setResizable(false);
        setBounds(0,0, WIDTH, HEIGHT);
        this.layout = new BorderLayout();
        setLayout(this.layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game of Life");
    }

    @Override
    public void visualizeGridChange(int x, int y, Action a) {

    }

    public Dimension getGridDimension(){ return gridDimension; }
}
