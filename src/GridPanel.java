import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel{
    JLabel[][] grid;

    public JLabel[][] getGrid(){
        return grid;
    }

    public GridPanel(int horizontal, int vertical) {
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
                label.setBackground(Color.GRAY);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                addToGrid(x, y, label);
                panel.add(label);
            }
        }
    }

    private void addToGrid(int x, int y, JLabel label){
        JLabel[][] grid = getGrid();
        grid[y][x] = label;
    }
}
