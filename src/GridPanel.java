import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class GridPanel extends JPanel implements MouseListener {
    private HashMap<JLabel, Point> coordinates = new HashMap<>();
    private JLabel[][] grid;
    private Selector selector;

    public JLabel[][] getGrid(){
        return grid;
    }

    public GridPanel(int horizontal, int vertical, Color color, Selector selector) {
        this.selector = selector;
        this.grid = new JLabel[vertical][horizontal];
        this.setLayout(new GridLayout(horizontal, vertical));
        JPanel panel = new JPanel();

        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(500,0));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setLayout(new GridLayout(horizontal, vertical));

        for(int y=0; y< vertical; y++){
            for(int x=0; x< horizontal; x++){
                JLabel label = new JLabel();
                label.setBackground(color);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                coordinates.put(label, new Point(x, y));
                label.addMouseListener(this);
                addToGrid(x, y, label);
                panel.add(label);
            }
        }
    }

    private void addToGrid(int x, int y, JLabel label){
        JLabel[][] grid = getGrid();
        grid[y][x] = label;
    }


    // -----------------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        Point point = coordinates.get(label);
        selector.select(point.x, point.y);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
