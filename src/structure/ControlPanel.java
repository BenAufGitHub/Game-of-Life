package structure;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

public class ControlPanel extends JPanel {

    public ControlPanel(){
        setLayout(null);
        setPreferredSize(new Dimension(300,300));
        setBackground(Color.RED);
    }
}
