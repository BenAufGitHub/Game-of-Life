package gol_extension.start;

import gol_extension.structure.GameOfLife;
import structure.ErrorHandler;
import structure.GUI;
import tools.ChoiceWindow;
import tools.SaveManager;

import javax.swing.JButton;
import java.awt.Point;
import java.io.IOException;

public class LoadButton extends JButton{

    private GameOfLife game;

    /**
     * loads save files
     * params: x/y = location
     */
    public LoadButton(GameOfLife game) {
        super("load");
        this.game = game;

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
                load(result);
        } catch (IOException ex) {
            ErrorHandler.catchError((GUI) getGame().getOutput(), ex, 6);
        }
    }


    public void load(String filename) throws IOException {
        getGame().reset();
        Point[] points = SaveManager.get(filename);
        for(Point point : points){
            getGame().clicked(point.x, point.y);
        }
    }


    public String chooseFile(){
        String[] choices = SaveManager.getSaveNames();
        ChoiceWindow picker = new ChoiceWindow(choices, (GUI) getGame().getOutput());
        picker.setVisible(true);
        return picker.getResult();
    }


    public GameOfLife getGame(){
        return game;
    }
}
