package gol_extension;

import structure.GUI;

import javax.swing.JComponent;

public class GUIExtensioner {
    private GUI gui;
    private GameOfLife game;


    public GUIExtensioner(GUI gui, GameOfLife game){
        this.gui = gui;
        this.game = game;
    }

    public GameOfLife getGame() { return game; }

    public GUI getGUI(){ return gui; }

    public void add(JComponent component){
        getGUI().addToControlPanel(component);
    }
}
