package gol_extension;

import structure.ErrorHandler;
import structure.GUI;
import tools.ChoiceWindow;
import tools.SaveManager;

import javax.swing.JButton;
import java.awt.Point;
import java.io.IOException;

public class SaveExtension extends GUIExtensioner{
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


    public void onSave(){
        save("save");
        //TODO prompt
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
