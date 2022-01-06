package gol_extension.saving;

import gol_extension.structure.GameOfLife;
import tools.SaveManagement;

import java.io.IOException;
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


    public SaveManagement getSaver(){
        return saver;
    }

}
