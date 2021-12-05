package structure;

import java.awt.Color;

/*
    possible extensions with: color, size of window
 */
public class Settings {
    private boolean changableColours;
    private Color standardColor;

    public Settings(Color gridStandard, boolean changableColours){
        this.standardColor = gridStandard;
        this.changableColours = changableColours;
    }

    public boolean coloursChangable(){
        return changableColours;
    }

    public Color getStandardGridColor(){
        return standardColor;
    }
}
