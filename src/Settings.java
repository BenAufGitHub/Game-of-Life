
/*
    possible extensions with: color, size of window
 */
public class Settings {
    private boolean showTracked = false;

    public Settings(boolean showTracked){
        this.showTracked = showTracked;
    }

    public void showTracked(boolean show){
        showTracked = show;
    }

    public boolean isTrackIndicated(){ return showTracked; }
}
