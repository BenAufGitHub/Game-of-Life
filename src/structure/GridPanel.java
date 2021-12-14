package structure;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
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

    public GridPanel(int horizontal, int vertical, Settings settings, Selector selector) {
        this.selector = selector;
        this.grid = new JLabel[vertical][horizontal];
        this.setLayout(new GridLayout(vertical, horizontal));

        setOpaque(false);
        setPreferredSize(new Dimension(700,700));

        for(int y=0; y< vertical; y++){
            for(int x=0; x< horizontal; x++){
                JLabel label = new JLabel();
                label.setBackground(settings.getStandardGridColor());
                label.setOpaque(true);
                if(!settings.isBorderless())
                    label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.BLACK));

                coordinates.put(label, new Point(x, y));
                label.addMouseListener(this);
                addToGrid(x, y, label);
                this.add(label);
            }
        }
    }

    private void addToGrid(int x, int y, JLabel label){
        JLabel[][] grid = getGrid();
        grid[y][x] = label;
    }

    public Selector getSelector(){
        return selector;
    }


    // -----------------------------------------------------------------------

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        Point point = coordinates.get(label);
        selector.select(point.x, point.y);
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
