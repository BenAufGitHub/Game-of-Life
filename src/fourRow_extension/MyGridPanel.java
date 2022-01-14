package fourRow_extension;

import structure.GridPanel;
import structure.Selector;
import structure.Settings;

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyGridPanel extends GridPanel implements MouseListener {

    public MyGridPanel(int horizontal, int vertical, Settings settings, Selector selector) {
        super(horizontal, vertical, settings, selector);
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mousePressed(e);
    }

    @Override
    public void mousePressed(MouseEvent e){ }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
