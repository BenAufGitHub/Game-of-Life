package gol_extension.start;

import gol_extension.saving.GOLSaver;
import gol_extension.structure.GameOfLife;
import structure.ErrorHandler;
import structure.GUI;
import tools.ChoiceWindow;

import javax.swing.JButton;
import java.io.IOException;

public class LoadButton extends JButton{

    private GameOfLife game;
    private GOLSaver<?> saver;

    /**
     * loads save files
     * params: x/y = location
     */
    public LoadButton(GameOfLife game, GOLSaver<?> saver) {
        super("load");
        this.game = game;
        this.saver = saver;

        this.addActionListener(e -> {
            if(!getGame().running())
                onLoad();
        });
    }


    /**
     * process of loading a save through a choice prompt
     */
    private void onLoad() {
        String result = chooseFile();
        try {
            if(result != null)
                saver.load(result);
        } catch (IOException ex) {
            ErrorHandler.catchError(ex, 6, false);
        }
    }


    public String chooseFile(){
        String[] choices = saver.getSaveNamesWithRightFormat();
        ChoiceWindow picker = new ChoiceWindow(choices, (GUI) getGame().getOutput());
        picker.setVisible(true);
        return picker.getResult();
    }


    public GameOfLife getGame(){
        return game;
    }
}
