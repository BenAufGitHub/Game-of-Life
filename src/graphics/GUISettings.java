package graphics;

public class GUISettings {
    private boolean allowColouring = false;

    public GUISettings(boolean allowColouring){
        this.allowColouring = allowColouring;
    }

    public void setAllowColouring(boolean bool){
       allowColouring = bool;
    }

    public boolean allowsColouring(){ return allowColouring; }
}
