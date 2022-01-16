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
        getClearButton().setText("start");
        getClearButton().addActionListener( e -> {
            Thread t = new Thread( () -> {
                if(getGame()==null) {return;}
                try {
                    deactivateButtons(Clicked.RUN);
                    getGame().run();
                    buttonsToDefault();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    ErrorHandler.catchError(this, ex, 2, true);
                }
            });
            t.start();
        });
    }


    public int getXCells(){
        return xCells;
    }

    public int getYCells(){
        return yCells;
    }
}