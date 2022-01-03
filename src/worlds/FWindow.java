package worlds;

import structure.ErrorHandler;
import structure.GUI;
import structure.Game;
import structure.Settings;
import structure.TimeSpanException;
import tools.ChoiceButton;
import tools.ChoiceListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

/**
 * extension of PureFWindow with additional features such as clear, speed and save Button
 */
public class FWindow extends PureFWindow {
    private List<JButton> staleButtonsOnRun;
    private JPanel controller;
    private JButton clear;
    private Factory.SpeedButton speedButton;

    public FWindow(int x, int y, Settings settings) {
        super(x, y, settings);

        this.staleButtonsOnRun = new ArrayList();
        this.controller = getControlPanel();
        this.clear = new JButton("clear");
        this.speedButton =  new Factory.SpeedButton(this);

        speedButton.setBounds(100, 350, 100, 30);
        speedButton.setFocusable(false);

        clear.setBounds(130, 400, 70, 30);
        clear.setFocusable(false);
        clear.addActionListener( e -> {
            if(getGame().running())
                return;
            getGame().reset();
            this.clear();
        });

        addButton(speedButton, true);
        addButton(clear, false);
    }


    /**
     * adds Button to ControlPanel.
     * @param activeOnRun specifies whether users should be able to click the button during game-runtime.
     */
    public void addButton(JButton button, boolean activeOnRun){
        if(!activeOnRun)
            staleButtonsOnRun.add(button);
        controller.add(button);
    }


    /**
     * note that each pause (slow to supersonic) should be progressively shorter
     * params: time of pause between game acts in Milliseconds
     */
    public void configureSpeedButton(int slowPause, int normalPause, int fastPause, int supersonicPause){
        speedButton.setGameSpeed(slowPause, normalPause, fastPause, supersonicPause);
    }


    public JButton getSpeedButton(){
        return speedButton;
    }


    /**
     deactivates all buttons that would cause unsafe threading when a specific button is pressed
     */
    @Override
    protected void deactivateButtons(GUI.Clicked button){
        super.deactivateButtons(button);
        if(button == GUI.Clicked.RUN || button == GUI.Clicked.RUN){
            allListedButtonsEnabled(false);
        }
    }

    @Override
    protected void buttonsToDefault(){
        super.buttonsToDefault();
        allListedButtonsEnabled(true);
    }


    private void allListedButtonsEnabled(boolean enabled){
        for(JButton button : staleButtonsOnRun)
            button.setEnabled(enabled);
    }


    /**
     * adjusts timeOutLength of an already existing game that is now associated with this window
     */
    @Override
    public void afterGameIsSet(){
        try {
            int timeOut = (speedButton != null) ? speedButton.getNormalPause() : 400;
            getGame().setTimeoutLength(timeOut);
        } catch (TimeSpanException e) {
            ErrorHandler.catchError(this, e, -1);
        }
    }


    /**
     * provides the worlds.ExtendedFixedWindow with specific practical Buttons
     */
    class Factory {


        /**
         * Button that changes state from: slow to normal to fast to supersonic to slow again etc.
         * custom game Pauses can be applied with setGameSpeed method
         */
        static class SpeedButton extends ChoiceButton implements ChoiceListener{

            private GUI window;
            private int slowPause = 800;
            private int normalPause = 400;
            private int fastPause = 90;
            private int supersonicPause = 35;


            private SpeedButton(GUI window) {
                super(new String[]{"Normal", "Fast", "Supersonic", "Slow"}, null);
                this.window = window;
                super.setListener(this);
            }


            @Override
            public void perform(String text) {
                Game game = window.getGame();
                try {
                    switch (text) {
                        case "Normal"-> game.setTimeoutLength(normalPause);
                        case "Fast" -> game.setTimeoutLength(fastPause);
                        case "Supersonic" -> game.setTimeoutLength(supersonicPause);
                        case "Slow" -> game.setTimeoutLength(slowPause);
                    }
                } catch(TimeSpanException e) {
                    ErrorHandler.catchError(null, e, -1);
                }
            }


            /**
             * note that each pause (slow to supersonic) should be progressively shorter
             * params: time of pause between game acts in Milliseconds
             */
            public void setGameSpeed(int slowPause, int normalPause, int fastPause, int supersonicPause){
                this.slowPause = slowPause;
                this.normalPause = normalPause;
                this.fastPause = fastPause;
                this.supersonicPause = supersonicPause;
            }


            /**
             * in milliseconds
             */
            public int getNormalPause(){
                return normalPause;
            }
        }
    }
}
