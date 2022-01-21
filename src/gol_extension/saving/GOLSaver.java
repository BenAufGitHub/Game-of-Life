package gol_extension.saving;

import gol_extension.structure.Cell;
import gol_extension.structure.GameOfLife;
import tools.SaveManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GOLSaver<Element> {

    private GameOfLife game;
    private SaveManagement<Element> saver;


    public GOLSaver(GameOfLife game, SaveManagement<Element> saver){
        this.game = game;
        this.saver = saver;
    }


    public void load(String fileName) throws IOException{
        getGame().reset();
        List<Element> list = saver.load(fileName);
        translateElements(list);
    }


    protected abstract void translateElements(List<Element> list);



    public void save(String fileName){
        saver.save(fileName, translateGame());
    }


    protected abstract List<Element> translateGame();


    public String[] getSaveNamesWithRightFormat(){
        return saver.getSaveNames();
    }


    public GameOfLife getGame(){
        return game;
    }


    public SaveManagement<Element> getSaver(){
        return saver;
    }


    // ---------------------------------------> Utility -----------------------------------------------


    protected List<Cell> getAliveCells(){
        ArrayList<Cell> list = new ArrayList<>();
        for(int x=0; x< getGame().getWidth(); x++){
            for(int y=0; y< getGame().getHeight(); y++){
                if(cellAlive(x,y)){
                    list.add(getCell(x,y));
                }
            }
        }
        return list;
    }


    private boolean cellAlive(int x, int y){
        return getGame().getCellTracker().getGrid().getCell(x,y).isAlive();
    }


    private Cell getCell(int x, int y){
        return getGame().getCellTracker().getGrid().getCell(x,y);
    }

}
