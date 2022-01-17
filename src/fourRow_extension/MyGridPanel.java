package fourRow_extension;

import structure.GridPanel;
import structure.Selector;
import structure.Settings;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyGridPanel extends GridPanel implements MouseListener {

    private boolean pressedCellNotExited = false;

    public MyGridPanel(int horizontal, int vertical, Settings settings, Selector selector) {
        super(horizontal, vertical, settings, selector);
        this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e){
        setInCellStatus(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setInCellStatus(false);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        JLabel label = (JLabel) e.getSource();
        if(!isPressedCellNotExited())
            return;
        super.mousePressed(e);
        setInCellStatus(false);
    }

    @Override
    public void mouseEntered(MouseEvent e){
        JLabel label = (JLabel) e.getSource();
        Point crds = getLabelCoordinates(label);
        getSelector().selectAsHovered(crds.x, crds.y);
    }

    // --------------------------------- private methods ----------------------------------------------

    private synchronized boolean isPressedCellNotExited(){
        return pressedCellNotExited;
    }

    private synchronized void setInCellStatus(boolean inPressedCell){
        this.pressedCellNotExited = inPressedCell;
    }
}
