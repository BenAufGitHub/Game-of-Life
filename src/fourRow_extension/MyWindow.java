package fourRow_extension;

import structure.Settings;
import worlds.HeaderFWindow;

import javax.swing.SwingConstants;

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
        configContinueButton();
    }

    private void configContinueButton() {
        //reuse run as continue button
        run.setText("continue");
        run.setEnabled(false);
        act.setEnabled(false);
    }

    private void configStartButton() {
        //reusing reset Button as reset and run Button
        getClearButton().setText("start");
        getClearButton().addActionListener( e -> {
            getGame().reset();
            triggerRunButton();
        });
    }


    @Override
    protected void buttonsToDefault() {
        super.buttonsToDefault();
        FourRow game = (FourRow) getGame();
        if(! game.isInGame()){
            run.setEnabled(false);
            act.setEnabled(false);
        }
    }

    public int getXCells(){
        return xCells;
    }

    public int getYCells(){
        return yCells;
    }
}