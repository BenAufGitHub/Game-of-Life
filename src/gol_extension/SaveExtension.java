package gol_extension;

import structure.ErrorHandler;
import structure.GUI;
import tools.ChoiceWindow;
import tools.EntryPopUp;
import tools.EntryVerification;
import tools.SaveManager;

import javax.swing.JButton;
import java.awt.Point;
import java.io.IOException;

public class SaveExtension extends GUIExtensioner implements EntryVerification{

    EntryPopUp popUp = null;

    public SaveExtension(GUI gui, GameOfLife game) {
        super(gui, game);
    }

    public void addLoadButton(int xPos, int yPos){
        JButton loader = createLoadButton(xPos,yPos);
        add(loader);
    }

    public void addSaveButton(int xPos, int yPos){
        JButton saver = createSaveButton(xPos, yPos);
        add(saver);
    }


    //------------------------------------------ saving -----------------------------------------------------------------

    /**
     * params: x/y = location
     */
    private JButton createSaveButton(int x, int y) {
        JButton save = new JButton("save");
        save.setBounds(x, y, 80, 25);
        save.setFocusable(false);
        save.addActionListener(e -> {
            if(!getGame().running())
                onSave();
        });
        return save;
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



    //------------------------------------ loading ------------------------------------------------------------


    /**
     * loads save files
     * params: x/y = location
     */
    private JButton createLoadButton(int x, int y) {
        JButton load = new JButton("load");
        load.setBounds(x, y, 80, 25);
        load.setFocusable(false);
        load.addActionListener(e -> {
            if(!getGame().running())
                onLoad();
        });
        return load;
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
            ErrorHandler.catchError(getGUI(), ex, 6);
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
        ChoiceWindow picker = new ChoiceWindow(choices, getGUI());
        picker.setVisible(true);
        return picker.getResult();
    }


    private boolean cellAlive(int x, int y){
        return getGame().getCellTracker().getGrid().getCell(x,y).isAlive();
    }
}
