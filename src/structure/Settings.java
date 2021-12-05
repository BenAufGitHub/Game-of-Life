package structure;

import java.awt.Color;

/*
    possible extensions with: color, size of window
 */
public class Settings {
    private boolean changableColours;
    private Color standardColor;
    private boolean borderless;

    public Settings(Color gridStandard, boolean changableColours){
        this.standardColor = gridStandard;
        this.changableColours = changableColours;
        this.borderless = false;
    }

    public Settings(Color gridStandard, boolean changableColours, boolean borderless){
        this.standardColor = gridStandard;
        this.changableColours = changableColours;
        this.borderless = borderless;
    }

    public boolean coloursChangable(){
        return changableColours;
    }

    public Color getStandardGridColor(){
        return standardColor;
    }

    public boolean isBorderless(){ return borderless; }
}
