package pong_extension;

import structure.Settings;

import java.awt.Color;

public class AccessPoint {

    public static void main(String[] args){
        int x = 150;
        int y = 150;

        Settings settings = new Settings(Color.WHITE, true, true);
        MyWindow window = new MyWindow(x,y, settings);
        new Pong(window);
        window.setVisible(true);
    }
}
