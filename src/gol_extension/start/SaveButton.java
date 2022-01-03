package gol_extension.start;

import gol_extension.structure.GameOfLife;
import structure.GUI;
import tools.EntryPopUp;
import tools.EntryVerification;
import tools.SaveManager;

import javax.swing.JButton;
import java.awt.Point;

public class SaveButton extends JButton implements EntryVerification {

    private EntryPopUp popUp = null;
    private GameOfLife game;


    /**
     * params: x/y = location
     */
    public SaveButton(GameOfLife game) {
        super("save");
        this.game = game;
        this.addActionListener(e -> {
            if(!getGame().running())
                onSave();
        });
    }


    public void onSave(){
        String saveName = nameFile();
        if(saveName != null)
            save(saveName);
    }


    public void save(String filename){
        int width = getGame().getWidth();
        int height = getGame().getHeight();

        Point[] points = new Point[width * height];
        int index = 0;
        for(int x=0; x< width; x++){
            for(int y=0; y< height; y++){
                if(cellAlive(x,y))
                    points[index++] = new Point(x, y);
            }
        }
        SaveManager.save(filename, points);
    }


    private String nameFile(){
        popUp = new EntryPopUp((GUI) getGame().getOutput());
        popUp.setEntryVerification(this);
        popUp.setTitle("Saving Cells");
        popUp.write("Enter your Save-name");
        popUp.setVisible(true);
        // Thread now awaits the pop-up to be closed

        if(popUp.noResult())
            return null;
        return popUp.getResult();
    }


    /**
     * overriding from entry Verification
     * String will be checked if it meets requirements (to be a save name)
     * if yes -> popUp closes and writes the String into its result (getResult())
     */
    @Override
    public boolean isValidEntry(String input) {
        input = input.strip();
        if(!isValidNameLength(input)){
            popUp.write("Name should have between 5 and 20 Characters.");
            return false;
        }
        if(!onlyValidCharacters(input)){
            popUp.write("Name can only contain 'a-z', 'A-Z', '0-9' and '_'.");
            return false;
        }
        return true;
    }


    /**
     * in regard to Save Names.
     */
    private boolean isValidNameLength(String s){
        return s.length() >= 5 && s.length() <= 20;
    }


    /**
     * Checks if save name has bad characters such as '*' or ';'.
     * Accepts letters, digits and underscore.
     */
    private boolean onlyValidCharacters(String word) {
        for(Character c : word.toCharArray()){
            if(!(Character.isLetter(c) || Character.isDigit(c) || c == '_'))
                return false;
        }
        return true;
    }


    public GameOfLife getGame(){
        return game;
    }

    private boolean cellAlive(int x, int y){
        return getGame().getCellTracker().getGrid().getCell(x,y).isAlive();
    }
}
