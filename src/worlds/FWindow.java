package worlds;

import structure.GUI;
import structure.Game;
import structure.Settings;
import structure.TimeSpanException;
import tools.ChoiceButton;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * extension of PureFWindow with additional features such as clear, speed and save Button
 */
public class FWindow extends PureFWindow {
    private JPanel controller;
    private JButton clear;
    private JButton choiceButton;

    public FWindow(int x, int y, Settings settings) {
        super(x, y, settings);

        this.controller = getControlPanel();
        this.clear = new JButton("clear");
        this.choiceButton =  Factory.createSpeedButton(this);

        choiceButton.setBounds(100, 350, 100, 30);
        choiceButton.setFocusable(false);

        clear.setBounds(130, 400, 70, 30);
        clear.setFocusable(false);
        clear.addActionListener( e -> {
            if(getGame().running())
                return;
            getGame().reset();
            this.clear();
        });

        controller.add(choiceButton);
        controller.add(clear);
    }


    /**
     * provides the worlds.ExtendedFixedWindow with specific practical Buttons
     */
    class Factory {


        /**
         * on click - changes behaviour from slow -> normal -> fast game.
         * creates Specific Button, that changes game breaks from 1000 ms -> 550 ms -> 100 ms -> 1000 ms and so on ...
         * @param window
         * @return
         */
        public static JButton createSpeedButton(GUI window){
            String[] choices = {"Slow", "Normal", "Fast"};
            return new ChoiceButton(choices, text -> {
                Game game = window.getGame();
                try {
                    switch (text) {
                        case "Normal", "Fast" -> game.setTimeoutLength(game.getTimeoutLength() - 450);
                        case "Slow" -> game.setTimeoutLength(game.getTimeoutLength() + 900);
                    }
                } catch(TimeSpanException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
