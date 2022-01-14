package fourRow_extension;

import structure.Settings;

import java.awt.Color;

public class AccessPoint {

    public static void main(String[] args){
        Settings settings = new Settings(Color.GRAY, true);
        MyWindow window = new MyWindow(7,6,settings);
        new FourRow(window);
        window.setVisible(true);
    }
}
