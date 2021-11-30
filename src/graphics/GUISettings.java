package graphics;

public class GUISettings {
    private boolean showTracked = false;

    public GUISettings(boolean showTracked){
        this.showTracked = showTracked;
    }

    public void showTracked(boolean show){
        showTracked = show;
    }

    public boolean isTrackIndicated(){ return showTracked; }
}
