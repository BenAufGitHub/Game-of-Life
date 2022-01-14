package fourRow_extension;

import structure.Settings;
import worlds.HeaderFWindow;

public class MyWindow extends HeaderFWindow {
    private int xCells;
    private int yCells;

    public MyWindow(int xCells, int yCells, Settings settings) {
        super(xCells, yCells, settings);
        this.xCells = xCells;
        this.yCells = yCells;
        setGridPanel(new MyGridPanel(xCells,yCells,settings, getGridPanel().getSelector()));
    }


    public int getXCells(){
        return xCells;
    }

    public int getYCells(){
        return yCells;
    }
}