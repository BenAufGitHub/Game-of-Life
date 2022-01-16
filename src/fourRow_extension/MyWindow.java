package fourRow_extension;

import structure.ErrorHandler;
import structure.GUI;
import structure.Settings;
import worlds.HeaderFWindow;

import javax.swing.*;

public class MyWindow extends HeaderFWindow {
    private int xCells;
    private int yCells;

    public MyWindow(int xCells, int yCells, Settings settings) {
        super(xCells, yCells, settings);
        this.xCells = xCells;
        this.yCells = yCells;
        setGridPanel(new MyGridPanel(xCells,yCells,settings, getGridPanel().getSelector()));
        setHeaderTextAlignment(SwingConstants.CENTER);
        configStartButton();
    }

    private void configStartButton() {
        //reusing reset Button as reset and run Button
        getClearButton().setText("start");
        getClearButton().addActionListener( e -> {
            getGame().reset();
            triggerRunButton();
        });
    }


    public int getXCells(){
        return xCells;
    }

    public int getYCells(){
        return yCells;
    }
}