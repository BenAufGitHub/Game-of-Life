package graphics;

public interface GUIRunnable {
    public Boolean awaiter = Boolean.FALSE;

    public void run() throws InterruptedException;
    public void act();
    public void stop();
    public boolean noProcess();
}
