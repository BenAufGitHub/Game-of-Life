package structure;

import java.awt.Color;

/**
    possible extensions with: color, size of window
 */
public class Settings {
    private final boolean changeableColours;
    private final Color standardColor;
    private final boolean borderless;

    public Settings(Color gridStandard, boolean changeableColours){
        this.standardColor = gridStandard;
        this.changeableColours = changeableColours;
        this.borderless = false;
    }

    public Settings(Color gridStandard, boolean changeableColours, boolean borderless){
        this.standardColor = gridStandard;
        this.changeableColours = changeableColours;
        this.borderless = borderless;
    }

    public boolean coloursChangeable(){
        return changeableColours;
    }

    public Color getStandardGridColor(){
        return standardColor;
    }

    public boolean isBorderless(){ return borderless; }
}
